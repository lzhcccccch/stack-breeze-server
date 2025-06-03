# User Authentication System API Documentation

## Overview

This document describes the complete user registration and login system implemented for the Spring Boot project. The system includes secure user registration, JWT-based authentication, and protected user profile endpoints.

## Features

- ✅ User Registration with validation
- ✅ User Login with JWT token generation
- ✅ Password encryption using BCrypt
- ✅ Email format validation and uniqueness
- ✅ JWT token-based authentication
- ✅ Protected user profile endpoint
- ✅ Global exception handling
- ✅ CORS configuration
- ✅ Comprehensive input validation

## Database Schema

First, create the user table in your MySQL database:

```sql
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密后）',
    `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标识：0 未删除；1 已删除',
    `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
```

## API Endpoints

### 1. User Registration

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
    "username": "testuser",
    "email": "test@example.com",
    "password": "TestPassword123"
}
```

**Validation Rules:**
- Username: 3-20 characters, alphanumeric and underscore only
- Email: Valid email format, max 100 characters
- Password: 6-50 characters, must contain at least one uppercase letter, one lowercase letter, and one number

**Success Response:**
```json
{
    "code": "0",
    "message": "请求成功",
    "data": {
        "id": "1234567890123456789",
        "username": "testuser",
        "email": "test@example.com",
        "createTime": "2024-12-19T10:30:00",
        "updateTime": null
    },
    "timestamp": 1703001000000
}
```

**Error Responses:**
- Username already exists: `A0015`
- Email already exists: `A0016`
- Validation errors: `A0001`

### 2. User Login

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
    "usernameOrEmail": "testuser",
    "password": "TestPassword123"
}
```

**Success Response:**
```json
{
    "code": "0",
    "message": "请求成功",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "tokenType": "Bearer",
        "expiresIn": 86400,
        "userInfo": {
            "id": "1234567890123456789",
            "username": "testuser",
            "email": "test@example.com",
            "createTime": "2024-12-19T10:30:00",
            "updateTime": null
        }
    },
    "timestamp": 1703001000000
}
```

**Error Responses:**
- Invalid credentials: `A0012`

### 3. Get User Profile (Protected)

**Endpoint:** `GET /api/auth/profile`

**Headers:**
```
Authorization: Bearer <your-jwt-token>
```

**Success Response:**
```json
{
    "code": "0",
    "message": "请求成功",
    "data": {
        "id": "1234567890123456789",
        "username": "testuser",
        "email": "test@example.com",
        "createTime": "2024-12-19T10:30:00",
        "updateTime": null
    },
    "timestamp": 1703001000000
}
```

**Error Responses:**
- Unauthorized: `A0019`
- Invalid token: `A0017`
- Token expired: `A0018`

## Configuration

### Environment Variables

You can configure the following environment variables:

```bash
# Database Configuration
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400  # 24 hours in seconds
```

### Application Properties

The system uses the following configuration in `application.yml`:

```yaml
app:
  jwt:
    secret: ${JWT_SECRET:mySecretKey123456789012345678901234567890123456789012345678901234567890}
    expiration: ${JWT_EXPIRATION:86400} # 24小时，单位：秒
```

## Security Features

1. **Password Encryption**: Uses BCrypt with default strength
2. **JWT Tokens**: Secure token-based authentication
3. **Input Validation**: Comprehensive validation using Bean Validation
4. **CORS Support**: Configured for cross-origin requests
5. **Global Exception Handling**: Standardized error responses

## Testing

Run the included tests to verify the authentication system:

```bash
mvn test -Dtest=AuthControllerTest
```

## Usage Examples

### Using cURL

**Register a new user:**
```bash
curl -X POST http://localhost:8090/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "TestPassword123"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "TestPassword123"
  }'
```

**Get profile (replace TOKEN with actual JWT):**
```bash
curl -X GET http://localhost:8090/api/auth/profile \
  -H "Authorization: Bearer TOKEN"
```

## Error Codes

| Code  | Message                |
|-------|------------------------|
| A0001 | 客户端错误             |
| A0010 | 用户不存在             |
| A0012 | 用户名或密码错误       |
| A0015 | 用户名已存在           |
| A0016 | 邮箱已存在             |
| A0017 | 无效的令牌             |
| A0018 | 令牌已过期             |
| A0019 | 未授权访问             |

## Next Steps

1. **Install Dependencies**: Run `mvn clean install` to install new dependencies
2. **Create Database Table**: Execute the SQL script to create the user table
3. **Test the APIs**: Use the provided test cases or manual testing
4. **Configure Environment**: Set up your JWT secret and database credentials
5. **Deploy**: The system is ready for production deployment

The authentication system is now fully implemented and ready to use!
