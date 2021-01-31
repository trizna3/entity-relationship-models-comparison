import os
path = 'dummy'
files = os.listdir(path)


for index, file in enumerate(files):
    os.rename(os.path.join(path, file), os.path.join(path, 's' + str(index+1) +'.jpg'))
