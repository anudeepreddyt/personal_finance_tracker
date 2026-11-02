# API documentation

Base URL: `http://localhost:8080/api`. Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`.

Authenticated endpoints expect `Authorization: Bearer <token>`.

## Authentication
- `POST /auth/register` — `{ "name":"Anu", "username":"anu", "email":"anu@example.com", "password":"at-least-8-chars" }`
- `POST /auth/login` — `{ "login":"anu@example.com", "password":"at-least-8-chars" }`; `login` accepts email or username.
- `GET /auth/me` — authenticated user profile.

## Transactions
- `GET /transactions?type=EXPENSE&category=Food&from=2026-01-01&to=2026-01-31`
- `POST /transactions` — `{ "type":"EXPENSE", "amount":125.50, "category":"Food", "description":"Lunch", "transactionDate":"2026-01-25" }`
- `GET /transactions/{id}`
- `PUT /transactions/{id}` — same payload as create.
- `DELETE /transactions/{id}`
- `GET /transactions/export.csv` — downloads the signed-in user's transactions as CSV.

## Budgets
- `GET /budgets?month=2026-01-01` — month may be any date in that month; use first of month for clarity.
- `POST /budgets` — `{ "month":"2026-01-01", "category":"Overall", "limitAmount":20000 }`
- `PUT /budgets/{id}` — same payload.
- `DELETE /budgets/{id}`

## Dashboard and analytics
- `GET /dashboard/summary?from=2026-01-01&to=2026-01-31`
- `GET /analytics/category-expenses`
- `GET /analytics/monthly-trends`

Errors use an HTTP status and JSON fields such as `status`, `error`, and (for validation) `details`. Users cannot access records owned by another user.
