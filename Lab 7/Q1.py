import sqlite3

conn = sqlite3.connect('student.db')
cursor = conn.execute('SELECT * FROM student ORDER BY name ASC')
print("Sorted by Name")
for row in cursor:
    print('\n―――――――――――――――――――――――――――――――――――――――――――\n\033[91mID: {}\033[0m\n\033[93mREG: {}\033[0m\n\033[92mName: {}\033[0m\n\033[96mBranch: {}\tSemester: {}\tSection: {}\033[0m\nEmail: {}\n\033[1mCGPA: {}\033[0m\n―――――――――――――――――――――――――――――――――――――――――――'.format(row[0],row[1],row[2],row[3],row[4],row[5],row[7],row[6]))
conn.close()