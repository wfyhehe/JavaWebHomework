# JavaWebHomework

## API Document
### Before you start
- in `application.properties`, complete your db config  

- create a superadmin(u=admin, p=admin) in your db:
```sql
insert into t_user (username, password, authority) 
values ("admin", "21232f297a57a5a743894a0e4a801fc3", 100);
```

### User
*Sign Up*

see `common.UserAuthority` for more info

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/user     | post |   superadmin     |

```json
{
  "username": "cj",
  "password": "123456",
  "authority": 1
}
```

----

*Sign In*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/user/signin     | post |   any     |

```json
{
  "username": "cj",
  "password": "123456"
}
```
return
```json
(your token)
```
place it in `Headers: Authorization:{token}` everytime you ajax

----
*Sign Out*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/user/signout     | post |   anthenticated     |

```json
{}
```
----
### Document

*List*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document     | get |   anthenticated     |


---- 

*Detail*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}     | get |   anthenticated     |


---- 

*Create*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document     | post |   gte CR    |
```json
{
    "title": "the document title",
    "content": "the content"
}
```

---- 

*Edit*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}     | put |   gte CRUD or CR and I am contributor     |
```json
{
    "title": "the document title",
    "content": "the content"
}
```

---- 

*Approve*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}/approve     | post |   gte CRUD     |
```json
{}
```

---- 

*Deny*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}/deny     | post |   gte CRUD     |
```json
{}
```


---- 

*Delete*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}     | delete |   gte CRUD     |
```json
{}
```


---- 

*Restore*

| url        | method   |  authority  |
| --------   | -----:  | :----:  |
| /api/document/{id}/restore     | post |   gte CRUD     |
```json
{}
```