package com.yimo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimo.common.Result;
import com.yimo.entity.*;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.*;
import com.yimo.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 性格测试服务实现
 * 算法来源: 塑说心语商业计划书 表4-1/表4-2
 * 五维人格模型 + 欧氏距离匹配 + 边界模糊规则
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonalityTestServiceImpl implements PersonalityTestService {

    private final PersonalityQuestionMapper questionMapper;
    private final PersonalityOptionMapper optionMapper;
    private final PersonalityResultMapper resultMapper;
    private final TestRecordMapper testRecordMapper;
    private final ZodiacMapper zodiacMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Result<List<Map<String, Object>>> getQuestions() {
        List<PersonalityQuestion> questions = questionMapper.selectList(
                new LambdaQueryWrapper<PersonalityQuestion>()
                        .eq(PersonalityQuestion::getStatus, 1)
                        .orderByAsc(PersonalityQuestion::getSortOrder));

        List<Map<String, Object>> result = new ArrayList<>();
        for (PersonalityQuestion question : questions) {
            List<PersonalityOption> options = optionMapper.selectList(
                    new LambdaQueryWrapper<PersonalityOption>()
                            .eq(PersonalityOption::getQuestionId, question.getId())
                            .orderByAsc(PersonalityOption::getSortOrder));

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", question.getId());
            map.put("questionText", question.getQuestionText());
            map.put("dimension", question.getDimension());
            map.put("questionType", question.getQuestionType());
            map.put("sortOrder", question.getSortOrder());
            map.put("options", options.stream().map(opt -> {
                Map<String, Object> optMap = new LinkedHashMap<>();
                optMap.put("id", opt.getId());
                optMap.put("optionText", opt.getOptionText());
                optMap.put("score", opt.getScore());
                return optMap;
            }).collect(Collectors.toList()));
            result.add(map);
        }
        return Result.success(result);
    }

    @Override
    @Transactional
    public Result<Map<String, Object>> submitTest(Map<Long, Integer> answers, Integer testDuration, Long userId) {
        List<PersonalityQuestion> questions = questionMapper.selectList(
                new LambdaQueryWrapper<PersonalityQuestion>().eq(PersonalityQuestion::getStatus, 1));

        // Step 1-2: 4D normalized vector + value type (商业计划书 表4-2)
        Map<String, Object> scoreResults = calculateVector(questions, answers);

        // Step 4: Euclidean distance matching
        Long matchedZodiacId = matchZodiac(scoreResults);

        PersonalityResult personalityResult = resultMapper.selectOne(
                new LambdaQueryWrapper<PersonalityResult>()
                        .eq(PersonalityResult::getZodiacId, matchedZodiacId).last("LIMIT 1"));
        Zodiac zodiac = zodiacMapper.selectById(matchedZodiacId);

        // Save record
        TestRecord record = new TestRecord();
        record.setUserId(userId);
        record.setZodiacId(matchedZodiacId);
        record.setResultId(personalityResult != null ? personalityResult.getId() : null);
        try {
            record.setAnswers(objectMapper.writeValueAsString(answers));
            record.setDimensionScore(objectMapper.writeValueAsString(scoreResults));
        } catch (JsonProcessingException e) { log.error("序列化失败", e); }
        record.setTestDuration(testDuration);
        testRecordMapper.insert(record);

        // Build response
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("recordId", record.getId());
        result.put("zodiacId", zodiac.getId());
        result.put("zodiacName", zodiac.getName());
        result.put("zodiacImage", zodiac.getImageUrl());
        result.put("zodiacAlias", zodiac.getAlias());
        result.put("title", personalityResult != null ? personalityResult.getTitle() : zodiac.getName());
        result.put("description", personalityResult != null ? personalityResult.getDescription() : zodiac.getPersonality());
        result.put("personality", zodiac.getPersonality());
        result.put("luckyColor", zodiac.getLuckyColor());
        result.put("luckyNumber", zodiac.getLuckyNumber());
        result.put("element", zodiac.getElement());
        result.put("dialectName", getDialectName(zodiac.getName()));

        if (personalityResult != null) {
            try {
                result.put("personalityTags", objectMapper.readValue(
                        personalityResult.getPersonalityTags() != null ? personalityResult.getPersonalityTags() : "[]",
                        new TypeReference<List<String>>() {}));
            } catch (Exception e) { result.put("personalityTags", List.of()); }
            result.put("strengths", personalityResult.getStrengths());
            result.put("weaknesses", personalityResult.getWeaknesses());
            result.put("careerAdvice", personalityResult.getCareerAdvice());
            result.put("relationshipAdvice", personalityResult.getRelationshipAdvice());
        }
        result.put("dimensionScores", scoreResults);
        result.put("shareImageUrl", personalityResult != null ? personalityResult.getShareImageUrl() : zodiac.getImageUrl());

        return Result.success(result);
    }

    @Override
    public Result<Map<String, Object>> getResult(Long recordId) {
        TestRecord record = testRecordMapper.selectById(recordId);
        if (record == null) throw new BusinessException(404, "测试记录不存在");

        Zodiac zodiac = zodiacMapper.selectById(record.getZodiacId());
        PersonalityResult pr = record.getResultId() != null ? resultMapper.selectById(record.getResultId()) : null;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("recordId", record.getId());
        result.put("zodiacId", zodiac.getId());
        result.put("zodiacName", zodiac.getName());
        result.put("zodiacImage", zodiac.getImageUrl());
        result.put("title", pr != null ? pr.getTitle() : zodiac.getName());
        result.put("description", pr != null ? pr.getDescription() : zodiac.getPersonality());
        result.put("personality", zodiac.getPersonality());
        result.put("luckyColor", zodiac.getLuckyColor());
        result.put("luckyNumber", zodiac.getLuckyNumber());
        result.put("element", zodiac.getElement());
        result.put("dialectName", getDialectName(zodiac.getName()));

        if (pr != null) {
            try { result.put("personalityTags", objectMapper.readValue(
                    pr.getPersonalityTags() != null ? pr.getPersonalityTags() : "[]", new TypeReference<List<String>>() {}));
            } catch (Exception e) { result.put("personalityTags", List.of()); }
            result.put("strengths", pr.getStrengths());
            result.put("weaknesses", pr.getWeaknesses());
            result.put("careerAdvice", pr.getCareerAdvice());
            result.put("relationshipAdvice", pr.getRelationshipAdvice());
        }
        try { result.put("dimensionScores", objectMapper.readValue(record.getDimensionScore(), Map.class));
        } catch (Exception e) { result.put("dimensionScores", Map.of()); }
        return Result.success(result);
    }

    @Override
    public Result<List<Map<String, Object>>> getTestHistory(Long userId) {
        List<TestRecord> records = testRecordMapper.selectList(
                new LambdaQueryWrapper<TestRecord>()
                        .eq(userId != null, TestRecord::getUserId, userId)
                        .orderByDesc(TestRecord::getCreateTime).last("LIMIT 20"));
        List<Map<String, Object>> result = new ArrayList<>();
        for (TestRecord r : records) {
            Zodiac z = zodiacMapper.selectById(r.getZodiacId());
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", r.getId());
            m.put("zodiacName", z != null ? z.getName() : "");
            m.put("zodiacImage", z != null ? z.getImageUrl() : "");
            m.put("createTime", r.getCreateTime());
            result.add(m);
        }
        return Result.success(result);
    }

    // ==================== Core Algorithm ====================

    /** 4D normalized vector: sum/sumMax → [-1,+1] for EI,SN,TF,JP */
    private Map<String, Object> calculateVector(List<PersonalityQuestion> questions, Map<Long, Integer> answers) {
        Map<String, Object> v = new LinkedHashMap<>();
        v.put("ei",  normalize("E/I", questions, answers));
        v.put("sn",  normalize("S/N", questions, answers));
        v.put("tf",  normalize("T/F", questions, answers));
        v.put("jp",  normalize("J/P", questions, answers));
        v.put("eiLabel", ((double)v.get("ei")) >= 0 ? "外向型" : "内向型");
        v.put("snLabel", ((double)v.get("sn")) >= 0 ? "实感型" : "直觉型");
        v.put("tfLabel", ((double)v.get("tf")) >= 0 ? "理性型" : "感性型");
        v.put("jpLabel", ((double)v.get("jp")) >= 0 ? "判断型" : "感知型");

        // Value dimension (4 questions, 1 per type)
        Map<String, Integer> vs = new LinkedHashMap<>();
        vs.put("ACHIEVE",0); vs.put("HARMONY",0); vs.put("EXPLORE",0); vs.put("RELATION",0);
        for (PersonalityQuestion q : questions) {
            if ("VALUE".equals(q.getDimension())) {
                Integer s = answers.get(q.getId());
                if (s != null && q.getPositiveScore() != null) vs.merge(q.getPositiveScore(), s, Integer::sum);
            }
        }
        String vt = vs.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("HARMONY");
        v.put("valueType", vt);
        v.put("valueLabel", Map.of("ACHIEVE","成就型","HARMONY","安稳型","EXPLORE","自由型","RELATION","关系型").getOrDefault(vt, "安稳型"));
        return v;
    }

    private double normalize(String dim, List<PersonalityQuestion> questions, Map<Long, Integer> answers) {
        int raw = 0, cnt = 0;
        for (PersonalityQuestion q : questions) {
            if (dim.equals(q.getDimension())) {
                Integer s = answers.get(q.getId());
                if (s != null) { raw += (q.getPositiveScore().equals(q.getNegativeScore()) ? s :
                        q.getPositiveScore().charAt(0) == dim.charAt(0) ? s : -s); cnt++; }
            }
        }
        return cnt > 0 ? Math.max(-1, Math.min(1, (double) raw / (cnt * 2.0))) : 0;
    }

    /** Table 4-2: Euclidean distance to 12 zodiac center points */
    private Long matchZodiac(Map<String, Object> v) {
        double ue = (Double) v.get("ei"), us = (Double) v.get("sn");
        double ut = (Double) v.get("tf"), uj = (Double) v.get("jp");
        String vt = (String) v.get("valueType");

        // Table 4-2 center points: [EI, SN, TF, JP]
        double[][] C = {{+0.7,+0.6,+0.5,+0.6},{-0.8,+0.7,+0.6,+0.8},{+0.9,-0.5,+0.8,+0.7},{-0.6,+0.5,-0.7,-0.5},{+0.8,-0.6,+0.7,+0.8},{-0.5,-0.7,+0.4,+0.3},{+0.9,+0.4,-0.6,-0.7},{-0.4,+0.6,-0.5,+0.2},{+0.8,-0.4,+0.3,-0.6},{+0.6,+0.5,+0.7,+0.9},{-0.3,+0.5,-0.4,+0.4},{+0.5,-0.3,-0.6,-0.5}};

        double[] D = new double[12];
        for (int i=0; i<12; i++) { double de=ue-C[i][0], ds=us-C[i][1], dt=ut-C[i][2], dj=uj-C[i][3]; D[i]=Math.sqrt(de*de+ds*ds+dt*dt+dj*dj); }

        int best=0, second=1; if (D[1]<D[0]) { best=1; second=0; }
        for (int i=2; i<12; i++) { if (D[i]<D[best]) { second=best; best=i; } else if (D[i]<D[second]) second=i; }

        // Table 4-1 boundary blurring: delta<0.05 → value-type priority
        if (D[second]-D[best] < 0.05) best = boundaryRule(vt, best, second, D);
        return (long)(best+1);
    }

    /** Table 4-1: 成就→虎(2)龙(4)鸡(9)  安稳→牛(1)兔(3)羊(7)  自由→马(6)猴(8)猪(11)  关系→狗(10)鼠(0) */
    private int boundaryRule(String vt, int a, int b, double[] D) {
        Set<Integer> P = switch(vt) { case "ACHIEVE"->Set.of(2,4,9); case "HARMONY"->Set.of(1,3,7); case "EXPLORE"->Set.of(6,8,11); case "RELATION"->Set.of(10,0); default->null; };
        if (P==null) return D[a]<=D[b]?a:b;
        boolean aIn=P.contains(a), bIn=P.contains(b);
        return (aIn&&!bIn)?a:(bIn&&!aIn)?b:(D[a]<=D[b]?a:b);
    }

    private static final Map<String,String> DIALECT = new HashMap<>();
    static { DIALECT.put("鼠","Cêh"); DIALECT.put("牛","Ghu"); DIALECT.put("虎","Hôun"); DIALECT.put("兔","Tòu"); DIALECT.put("龙","Lêng"); DIALECT.put("蛇","Zuê"); DIALECT.put("马","Bhê"); DIALECT.put("羊","Iên"); DIALECT.put("猴","Gao"); DIALECT.put("鸡","Goi"); DIALECT.put("狗","Gao"); DIALECT.put("猪","De"); }
    private String getDialectName(String name) { return DIALECT.getOrDefault(name, name); }
}
