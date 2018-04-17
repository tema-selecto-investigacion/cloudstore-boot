```sql
mysqldump --add-drop-database --add-drop-table --databases tpcw --user <user> --host <host> --port <port> > data-mysql.sql

mysql --user <user> --password <passworkd> --host <host> --port <port>  > data-mysql.sql
```