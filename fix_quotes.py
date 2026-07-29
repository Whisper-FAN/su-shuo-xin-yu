import glob, re

for fpath in glob.glob('yimo-web/src/**/*.vue', recursive=True):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original = content
    
    # Fix all variants of escaped quotes in :src bindings
    # Pattern: base + \'/images/xxx.xxx\'
    # Should be: base + '/images/xxx.xxx'
    content = re.sub(
        r":src=\"base \+ \\?'/images/(.+?)\.(jpg|png|JPG)\\?'\"",
        r':src="base + \'/images/\1.\2\'"',
        content
    )
    
    if content != original:
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f'Fixed: {fpath}')

print('Done')
