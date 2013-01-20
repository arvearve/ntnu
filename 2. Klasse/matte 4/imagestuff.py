import matplotlib.pyplot as plt
import numpy as np
import matplotlib.cm as cm

def readpgm(filename):
    f = open(filename, 'rb')
    header = f.readline().strip()
    if header != 'P5':
        raise Exception('Feil filformat.')
    header = f.readline().strip().split(' ')
    while (header[0] == '#'):
        header = f.readline().strip().split(' ')
    w = int(header[0])
    h = int(header[1])
    header = f.readline().strip().split(' ')
    while (header[0] == '#'):
        header = f.readline().strip().split(' ')
    m = int(header[0])
    img = np.fromfile(f, dtype=np.uint8).reshape((h, w))
    f.close()
    return img

def writepgm(img, filename):
    f = open(filename, 'wb')
    f.write('P5\n')
    (h,w) = img.shape
    f.write('%d %d\n' %(w,h))
    f.write('255\n')
    img2 = img.astype('uint8')
    print img2
    img2.tofile(f, format='%d')
    f.close()

def showimage(img):
    fig = plt.figure()
    ax = fig.add_subplot(1,1,1)
    ax.imshow(img, cmap=cm.gray)
    plt.show()
