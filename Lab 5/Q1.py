class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

def matrixX(a,b,mat):
    p = a
    q = b
    a = mat
    return [a[i:i+q] for i in range(0,len(a),q)]

def matrix():
    print(bcolors.OKCYAN,end='')
    p = int(input('Enter row dimensions: '))
    q = int(input('Enter column dimensions: '))
    a = list(map(int,input('Enter all matrix values: ').split(' ')[:p*q]))
    print(bcolors.ENDC,end='')
    return [a[i:i+q] for i in range(0,len(a),q)]

def mSet():
    print(bcolors.OKCYAN,end='')
    a = set(map(int,input('Enter all set values: ').split(' ')))
    print(bcolors.ENDC,end='')
    return a

def encrypt(string, shift):
  cipher = ''
  for char in string: 
    if char == ' ':
        cipher = cipher + char
    elif char.isupper():
        cipher = cipher + chr((ord(char) + shift - 65) % 26 + 65)
    else:
        cipher = cipher + chr((ord(char) + shift - 97) % 26 + 97)
  return cipher
    
# Part 1.a

print(bcolors.OKCYAN,end='')
x = input('Enter an expression: ').strip()
print(bcolors.ENDC,end='')
print(x,'=',eval(x))


# Part 1.b

a = matrix()
b = [[i[j] for i in a] for j in range(len(a[0]))]
print('Matrix: {}\nTranspose: {}'.format(a,b))


# Part 1.c

a = matrix()
b = matrix()
c = [sum([a[i][k]*b[k][j] for k in range(len(a[0]))]) for i in range(len(a)) for j in range(len(b[0]))]
c = matrixX(len(a),len(b[0]),c)
print('Matrix A: {}\nMatrix B: {}\nMatrix A×B: {}'.format(a,b,c))

# Part 1.d

a = matrix()
b = matrix()
c = [a[i][j]+b[i][j] for i in range(len(a)) for j in range(len(a[0]))]
c = matrixX(len(a),len(a[0]),c)
print('Matrix A: {}\nMatrix B: {}\nMatrix A+B: {}'.format(a,b,c))

# Part 1.e

def sum(n):
    if n == 1:
        return n
    return n+sum(n-1)

print(bcolors.OKCYAN,end='')
n = int(input('Enter n: '))
print(bcolors.ENDC,end='')
print('{} = {}'.format(' + '.join(list(map(str,list(range(1,n+1))))),sum(n)))

# Part 1.f

def fibonacci(a,b,n):
    if n == 0:
        return
    print(a,end=' ')
    c = b
    b = a + c
    a = c
    fibonacci(a, b, n - 1)

print(bcolors.OKCYAN,end='')
n = int(input('Enter n: '))
print(bcolors.ENDC,end='')
fibonacci(0, 1, n)

# Part 1.g

print(bcolors.OKCYAN,end='')
n = int(input('Enter n: '))
print(bcolors.ENDC,end='')
print('Number: {}\nBinary: {:b}\nOctal: {:o}\nHex: {:x}'.format(n,n,n,n))

# Part 1.h

a = mSet()
b = mSet()
print('A: {}\nB: {}\n―――――――――――――――――――――――――――――――――――\nA ∪ B: {}\nA ∩ B: {}\nA - B: {}\nB - A: {}\nA ∆ B: {}'.format(a,b,a|b,a&b,a-b,b-a,a^b))

# Part 1.i

print(bcolors.OKCYAN,end='')
a = list(map(int,input('Enter values: ').split(' ')))
print(bcolors.ENDC,end='')
print(a,'is',('is not a palindrome','is a palindrome')[a == a[::-1]])

# Part 1.j

print(bcolors.OKCYAN,end='')
a = str(input('Enter string: '))
n = int(input('Enter shift: '))
print(bcolors.ENDC,end='')
print('{} -- {} --> {}'.format(a,n,encrypt(a, n)))