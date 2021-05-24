import re
import os
import pprint

pp = pprint.PrettyPrinter(indent=4)

# Part 1.a

x = input('Enter an string: ').strip()
print(x,('is not a palindrome','is a palindrome')[x == x[::-1]])

# Part 1.b

x = input('Enter an sentence: ').strip()
re.sub(r'[,.;\'"?!:]','',x)

# Part 1.c

x = input('Enter an sentence: ').strip()
x = x.split()
print(' > '.join(sorted(x)))

# Part 1.d

# Uhm ??????

# Part 1.e

x = input('Enter an sentence: ').strip()
y0 = input('Enter word to be replaced: ').strip()
y1 = input('Enter word to be replaced with: ').strip()
print('{} -> {}\nNew Sentence: {}'.format(y0,y1,re.sub(y0,y1,x)))

# Part 1.f

x = dict(os.environ)
pp.pprint(x)