import sqlite3
import re
import getpass
from typing import Type

def valid_input(message,regex,err):
    while True:
        input_string = input(message)
        if not re.match(regex,input_string):
            print("\033[91m"+err+"\033[0m")
        else:
            return input_string

id = valid_input("Enter ID: ",r"^\d{5}$","Invalid ID")
regno = valid_input("Enter 9 digit Registration Number: ",r"^\d{9}$","Invalid Registration Number")
name = valid_input("Enter Name: ",r"^\D+$","Invalid name")
branch = input("Enter Branch: ")
semester = int(valid_input("Enter Semester(1/2/3/4/5/6/7/8): ",r"^\d{1}$","Invalid Semester"))
section = valid_input("Enter Section: ",r"^\D{1}$","Invalid Section")
while True:
    try:
        cgpa = round(float(input("Enter CGPA: ")),2)
        break
    except ValueError:
        print("\033[91mInvalid CGPA\033[0m")
email = valid_input("Enter Email: ",r"^(\w|\.|\_|\-)+[@](\w|\_|\-|\.)+[.]\w{2,3}$","Invalid Email")

row = (id,regno,name,branch,semester,section,cgpa,email)
conn = sqlite3.connect('student.db')
conn.execute('INSERT INTO student VALUES(?,?,?,?,?,?,?,?)',row)
print('\n―――――――――――――――――――――――――――――――――――――――――――\n\033[91mID: {}\033[0m\n\033[93mREG: {}\033[0m\n\033[92mName: {}\033[0m\n\033[96mBranch: {}\tSemester: {}\tSection: {}\033[0m\nEmail: {}\n\033[1mCGPA: {}\033[0m\n―――――――――――――――――――――――――――――――――――――――――――'.format(row[0],row[1],row[2],row[3],row[4],row[5],row[7],row[6]))
if int(input("\033[91mAre you sure you want to add: \033[0m\n[0] No\t[1] Yes\n> ")):
    conn.commit()
conn.close()