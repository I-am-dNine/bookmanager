# 📦 BookManager v1.0.0

第一個穩定版本，實作了基本借閱者管理與 JWT 驗證系統，並完成 Redis 黑名單登出機制。

## ✨ 功能清單

- ✅ 使用者註冊 / 登入 / 登出（JWT 驗證）
- ✅ 借閱者 CRUD 操作（支援權限管控）
- ✅ JWT 黑名單登出處理（使用 Redis）
- ✅ 安全限制：只能修改/查詢自己，需角色才可新增/刪除
- ✅ Swagger API 文件整合
- ✅ Docker 化部署（含 docker-compose.yml）
- ✅ 統一 API Response 格式（ApiResponseDto）

## 🐳 Docker 支援

使用 `docker-compose up` 一鍵啟動後端與 Redis 環境。

## 🧪 測試帳號

- admin：alice@example.com / 123456
- staff：bob@example.com / 123456
- user：cathy@example.com / 123456

## 📁 文件參考

請參閱專案內 [`README.md`](./RELEASE_NOTES.md) 獲得詳細說明。
