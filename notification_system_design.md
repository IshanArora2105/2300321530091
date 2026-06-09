# Stage 1

## APIs

GET /notifications

Returns all notifications of a student.

POST /notifications

Creates a new notification.

PUT /notifications/{id}/read

Marks a notification as read.

Real Time Notifications:

Notifications can be automatically refreshed whenever a new notification is received.

---




# Stage 2

Database: PostgreSQL

Reasons:

* Reliable
* Easy to manage
* Supports large amounts of data

Notification Table:

* id
* studentId
* type
* message
* isRead
* createdAt

Handling Growth:

* Indexing
* Pagination
* Archiving old notifications

---



# Stage 3

The query is correct because it fetches unread notifications of a student and sorts them by latest first.

Problem:

* Large number of notifications
* Query may become slow as data grows

Recommended Index:

CREATE INDEX idx_notifications
ON notifications(studentId, isRead, createdAt DESC);

Should every column be indexed?

No.

Reasons:

* More storage required
* Slower inserts
* Slower updates

Placement notifications in last 7 days:

SELECT DISTINCT studentId
FROM notifications
WHERE notificationType = 'Placement'
AND createdAt >= NOW() - INTERVAL '7 DAY';

---




# Stage 4

To reduce database load, frequently accessed notifications can be stored temporarily in memory.

Flow:

Client
↓
Memory Cache
↓
Database

Benefits:

* Faster response
* Reduced database load

Tradeoff:

* Extra memory usage

---




# Stage 5

Problems:

* Slow processing
* No retry mechanism
* Partial failures possible

Solution:

1. Save notification in database.
2. Process email notifications separately.
3. Process app notifications separately.

Benefits:

* Faster execution
* Better reliability
* Easier maintenance