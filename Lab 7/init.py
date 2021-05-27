import sqlite3

data =[
    ("20284","180911202","Nikesh Kumar","IT",6,"B",9.20,"nikeplusdash@gmail.com"),
    ("20288","180911222","Suresh Pandit","IT",6,"B",9.61,"sureshsharma@gmail.com"),
    ("20274","180911132","Prushad Karma","IT",6,"A",8.24,"prutabh@gmail.com"),
    ("20282","180911234","Keyari Kapoor","IT",6,"B",7.97,"keyaruli@gmail.com"),
    ("20301","180911221","Poko Chris","IT",6,"A",9.02,"porklsi@gmail.com")
]

conn = sqlite3.connect('student.db')
conn.execute("""
CREATE TABLE IF NOT EXISTS student(
    id CHAR(5),
    reg CHAR(9),
    name TEXT,
    branch TEXT,
    semester INT,
    section CHAR(1),
    cgpa DECIMAL(4,2),
    email TEXT,
    PRIMARY KEY(id,reg)
);
""")
conn.executemany('INSERT INTO student VALUES(?,?,?,?,?,?,?,?)',data)
conn.commit()