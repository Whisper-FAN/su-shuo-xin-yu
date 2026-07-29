import urllib.request, os
BASE = r'http://localhost:3000'
base_dir = r'yimo-web\public'
all_files = []
for root, dirs, files in os.walk(base_dir):
    for f in files:
        rel = os.path.join(root, f).replace(base_dir, '')
        rel = rel.replace(os.sep, '/')
        all_files.append(rel)

ok, fail = 0, 0
for path in all_files:
    try:
        req = urllib.request.Request(BASE + path, method='HEAD')
        resp = urllib.request.urlopen(req, timeout=5)
        if resp.status == 200:
            ok += 1
        else:
            print(f'  {path}: HTTP {resp.status}')
            fail += 1
    except Exception as e:
        print(f'  {path}: {type(e).__name__}')
        fail += 1

print(f'Local files: {ok} OK, {fail} FAIL out of {len(all_files)}')

print()
print('Backend APIs:')
for api in ['/api/zodiac/list', '/api/test/questions', '/api/banner/list', '/api/product/hot?limit=3']:
    try:
        req = urllib.request.Request(f'{BASE}{api}')
        resp = urllib.request.urlopen(req, timeout=5)
        print(f'  {api}: HTTP {resp.status}')
    except Exception as e:
        print(f'  {api}: {type(e).__name__}')

print()
print('External deps:')
deps = [
    ('Google Fonts', 'https://fonts.googleapis.com/css2?family=Noto+Serif+SC:wght@400;600;700'),
    ('Paper Texture 1', 'https://www.transparenttextures.com/patterns/natural-paper.png'),
    ('Paper Texture 2', 'https://www.transparenttextures.com/patterns/handmade-paper.png'),
    ('QR Code API', 'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=test'),
]
for label, url in deps:
    try:
        req = urllib.request.Request(url, method='HEAD')
        resp = urllib.request.urlopen(req, timeout=5)
        print(f'  {label}: HTTP {resp.status}')
    except Exception as e:
        print(f'  {label}: {type(e).__name__}')
