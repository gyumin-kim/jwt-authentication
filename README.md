# jwt-authentication

Spring Boot로 JWT Authentication 구현하기

```shell script
# JWT 없이 GET 요청 날리기
# 403 forbidden 에러가 발생한다
curl http://localhost:8080/tasks

# 새로운 User 등록
curl -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/users/sign-up

# application에 로그인 (JWT 발행)
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login

# Task를 생성하기 위해 POST 요청 (JWT를 같이 넘긴다)
# www.yyy.zzz 대신에 위에서 발급받은 JWT를 넣어줘야 한다
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer xxx.yyy.zzz" \
-X POST -d '{
    "description": "Buy watermelon"
}'  http://localhost:8080/tasks

# GET 요청 (JWT를 같이 넘긴다)
# www.yyy.zzz 대신에 위에서 발급받은 JWT를 넣어줘야 한다
curl -H "Authorization: Bearer xxx.yyy.zzz" http://localhost:8080/tasks
```