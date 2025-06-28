# Initial definition of system entities

[Book]
- id
- title
- author
- isbn
- stock

[Reader]
- id
- name
- email

[BorrowRecord]
- id
- book_id (FK)
- reader_id (FK)
- borrow_date
- return_date

---

[Book]                    [Reader]
- id                     - id
- title                  - name
- author                 - email
- isbn
- stock

         ↘           ↙
         [BorrowRecord]
         - id
         - book_id (FK)   ← 指向 Book.id
         - reader_id (FK) ← 指向 Reader.id
         - borrow_date
         - return_date


- 一个 Book 可以被多个 BorrowRecord 借走（一对多）
- 一个 Reader 也可以借多本书（一对多）
- BorrowRecord 表中同时关联了 Book 与 Reader（多对一）