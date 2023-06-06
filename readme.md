## api spec

### base url -> localhost:8080/api

### Authorization server oauth2 jwt spring boot
    
 - /auth/register
   - request body
    ```json
    {
        "username" : "ya" , 
        "password" : "rahasia",
        "firstName" : "zam",
        "lastName" : "zami",
        "email" : "mohammadtajutzamzami07@gmail.com"
    
    }
    ```
   - response body
   ```json
    {
    "data": {
        "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhIiwiZXhwIjoxNjg2MDMxNTk3LCJpYXQiOjE2ODYwMzEyOTd9.FkwvGituWbymVw8KDTy43nRQuYI2ZauFUpxN4sr9kz9PX-o3iN9eTxmq6GWSjxy3Cvl2ufPoo1F8MjeBjF9RJHRNLnfRoxaa-t5epVpKWY-HJ-bfbtC1URk70kuKd4hROdz2cup55CRXeedkTxS6AhC3mB06-4kDo5sh2r7uKB6-IAAezwGTuaTio3hTrQ-QJnIzYZPfpJ8ML18O0kFJhwlQhtLFs4L-af5vrxfPSgw4XE7Vj5kDHourN6f0FnVg4p8QjCGAQZfgL5HByQjU2VwbiXCf8ywMZh5RWmqvReOwbggZP76RrD3nUwBNOGoJ1lLDphqoamcJK9U6rOCduQ",
        "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhIiwiZXhwIjoxNjg2MDMxNDE3LCJpYXQiOjE2ODYwMzEyOTd9.ZK026XslaRCkoPWeA6t7l7IFQWbdg3GpJsLx-S1sKuD1phJO8mxh1NGPtJwDCJbatHUi3NJLPmecQ0ZnQj2waQndys0PEm88Ew27kj8gyJBS7ynwqsS8bgWwWbChmvOpKB-hSRxkzFTtLrjtCwCtw0S09hXBYOGw3ZC54Iq0_wV_kX76JgC4aeuCkJDLt9YP1dU4lEMnUjUiVGMAuJrswMgjGMI9PWhFNAZgo__HnAyQw4T18ne5rJXi9Tb52DlPvNMsOQcsRKFGy5UOb97A7st2TpBpqlOB4NuKvgeORWpPbBKiK-AaLls4huAXN414sS9vwKUKfio3dDKuZ6Ejgw"
    },
    "errors": null
    }
 - /auth/login
 ### request body
 ```json
    {
    "username" : "yas",
    "password" : "rahasia"
    } 
 ```
### response body
```json
    {
    "data": {
        "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhcyIsImV4cCI6MTY4NjAzMTY3MywiaWF0IjoxNjg2MDMxMzczfQ.Ux7_6reMSuLDj9TwSlwKMsEUfp4EThHNuonqeMJz5U7o81VrSu7PNiT6Pjtd98x_-O9rLColbijoEJmyZ-knQKKEAV1kchssdVdJUfgY2wmauTBAIogwYlHVB3bghg0cRkgHHxbDrEA4Q0_I5rb9DeGhhcvkED3nhuAW2tZu9loTem89QXN539YlKrejd125llhjlLdSq0uZBs3b5esZcz7iXTlObRIKk-mnGam_PNrW-TMf9ragSvlIQU4d0CrMmG7Ei9xJ1FAsBmWyV-tsNq5eGi_GR6CA67GJqTII9oAx4vDO6IGSQGW1yI8QOgHZM6zTUVFYWnC-FJa8cBfSgw",
        "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhcyIsImV4cCI6MTY4NjAzMTQ5MywiaWF0IjoxNjg2MDMxMzczfQ.XBfsQiOe9EBbMnV942TE0C96O5RtGCLs2lzgO6S7Vd8uvr5qJbZcOXQ4p0azDNoqZKqWJ_47p9Tt_gjOGFWOsxyQsekNjPIGAmtQqpaOiFshQx00oC6hUtnkcX87tYXoR00jGhfPCya8aq_qKeiK6sQ81Kg2XrAov18YP5FOVphE5JrzFuUjdia5enCsACiGrHpVVzl6jl1TACTLWT7tHrgTQcmCibtrsG1PMrYzcY6NQTYfbnEsUfQt9q7iud1pfgYdp7XSf_DtnKq0CaoRUHMC96AFipoOfvmQrA8rx1WhUuJe6mCYQ8-ggB7Ks473PTFVnubT_2tsu2FI0vSY1g"
    },
    "errors": null
}
```

- /auth/token
  - request body
  ```json
  {
  "refreshToken" : "string"
  }
    ```
 - response body
    ```json

    {
    "data": {
        "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhcyIsImV4cCI6MTY4NjAzMTY3MywiaWF0IjoxNjg2MDMxMzczfQ.Ux7_6reMSuLDj9TwSlwKMsEUfp4EThHNuonqeMJz5U7o81VrSu7PNiT6Pjtd98x_-O9rLColbijoEJmyZ-knQKKEAV1kchssdVdJUfgY2wmauTBAIogwYlHVB3bghg0cRkgHHxbDrEA4Q0_I5rb9DeGhhcvkED3nhuAW2tZu9loTem89QXN539YlKrejd125llhjlLdSq0uZBs3b5esZcz7iXTlObRIKk-mnGam_PNrW-TMf9ragSvlIQU4d0CrMmG7Ei9xJ1FAsBmWyV-tsNq5eGi_GR6CA67GJqTII9oAx4vDO6IGSQGW1yI8QOgHZM6zTUVFYWnC-FJa8cBfSgw",
        "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJteUFwcCIsInN1YiI6InlhcyIsImV4cCI6MTY4NjAzMTQ5MywiaWF0IjoxNjg2MDMxMzczfQ.XBfsQiOe9EBbMnV942TE0C96O5RtGCLs2lzgO6S7Vd8uvr5qJbZcOXQ4p0azDNoqZKqWJ_47p9Tt_gjOGFWOsxyQsekNjPIGAmtQqpaOiFshQx00oC6hUtnkcX87tYXoR00jGhfPCya8aq_qKeiK6sQ81Kg2XrAov18YP5FOVphE5JrzFuUjdia5enCsACiGrHpVVzl6jl1TACTLWT7tHrgTQcmCibtrsG1PMrYzcY6NQTYfbnEsUfQt9q7iud1pfgYdp7XSf_DtnKq0CaoRUHMC96AFipoOfvmQrA8rx1WhUuJe6mCYQ8-ggB7Ks473PTFVnubT_2tsu2FI0vSY1g"
    },
    "errors": null

```

- /user/
  - header
  #### bearer token (required)
 
## response body

```json
[
    {
        "username": "ya",
        "password": "$2a$10$e6BcCMD4Kh/E44KnBbMJOuq/k04EnEuD3blWpJaXbo/jhsh9dPklq",
        "firstName": "zam",
        "lastName": "zami",
        "email": "mohammadtajutzamzami07@gmail.com",
        "enabled": true,
        "authorities": [
            {
                "authority": "user"
            }
        ],
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true
    },
    {
        "username": "yas",
        "password": "$2a$10$hO.RiG6RpQMwYeFJYeM61uOW1LI1Tt3PD9q2rROGKeLJi2vjX3qAy",
        "firstName": "zam",
        "lastName": "zami",
        "email": "mohammadtajutzamzami07@gmail.com",
        "enabled": true,
        "authorities": [
            {
                "authority": "user"
            }
        ],
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true
    }
]
```
    
