from os import sep
import sqlite3
import re

def valid_input(message,regex,err):
    while True:
        input_string = input(message)
        if not re.match(regex,input_string):
            print("\033[91m"+err+"\033[0m")
        else:
            return input_string

regno = valid_input("Enter 9 digit Registration Number: ",r"^\d{9}$","Invalid Registration Number")
while True:
    try:
        cgpa = round(float(input("Enter CGPA: ")),2)
        break
    except ValueError:
        print("\033[91mInvalid CGPA\033[0m")
conn = sqlite3.connect('student.db')
initial_row = conn.execute('SELECT * FROM student WHERE reg = ?',[regno]).fetchall()[0]
cursor = conn.execute('UPDATE student SET cgpa = ? WHERE reg = ?',(cgpa,regno))
final_row = conn.execute('SELECT * FROM student WHERE reg = ?',[regno]).fetchall()[0]
change_list = list(zip(initial_row,final_row))
header = ['ID','REG','Name','Branch','Semester','Section','CGPA','Email']
output = "\n―――――――――――――――――――――――――――――――――――――――――――             ―――――――――――――――――――――――――――――――――――――――――――"
changed = "\033[93m{}\033[0m"
for i,column in enumerate(change_list):
    # if column[0] == column[1]:
    line1 = header[i] + ": " + str(column[0])
    line2 = header[i] + ": " + str(column[1])
    seperator = ("\t \t","\t→\t")[column[0] != column[1]]
    if column[0] != column[1]:
        output += "\n\033[93m{:<43} {} {:<43}\033[0m".format(line1,seperator,line2)
    else:
        output += "\n{:<43} {} {:<43}".format(line1,seperator,line2)
output += "\n―――――――――――――――――――――――――――――――――――――――――――             ―――――――――――――――――――――――――――――――――――――――――――"
print(output)
if int(input("\033[91mAre you sure you want to add: \033[0m\n[0] No\t[1] Yes\n> ")):
    conn.commit()
conn.close()