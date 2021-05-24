import sqlite3
import re
import getpass

conn = sqlite3.connect('student.db')
is_pass = False
while True:
    user_id = input("Enter Username: ")
    password = getpass.getpass("Enter Password: ")
    re_password = getpass.getpass("Re-enter Password: ")
    validate_user,validate_pwd,validate_re_pwd = False,False,False
    if re.match('^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9])+[a-zA-Z0-9]$',user_id):
        validate_user = True
    if re.match('^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\d)(?=.*[!#$%&? "]).*$',password):
        validate_pwd = True
    if password == re_password:
        validate_re_pwd = True
    if(not validate_user or not validate_pwd or not validate_re_pwd):
        print(((("Password not matching","")[validate_re_pwd],"Invalid Password")[validate_pwd == None],"Invalid Username")[validate_user == None])
    else:
        break
