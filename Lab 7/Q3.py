import sqlite3
import re

def valid_input(message,regex,err):
    while True:
        input_string = input(message)
        if not re.match(regex,input_string):
            print("\033[91m"+err+"\033[0m")
        else:
            return input_string

regno = [valid_input("Enter 9 digit Registration Number: ",r"^\d{9}$","Invalid Registration Number")]
conn = sqlite3.connect('student.db')
cursor = conn.execute('SELECT * FROM student WHERE reg = ?',regno)
for row in cursor:
    print('\n―――――――――――――――――――――――――――――――――――――――――――\n\033[91mID: {}\033[0m\n\033[93mREG: {}\033[0m\n\033[92mName: {}\033[0m\n\033[96mBranch: {}\tSemester: {}\tSection: {}\033[0m\nEmail: {}\n\033[1mCGPA: {}\033[0m\n―――――――――――――――――――――――――――――――――――――――――――'.format(row[0],row[1],row[2],row[3],row[4],row[5],row[7],row[6]))
conn.close()