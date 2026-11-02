# FinTrack — Personal Finance Tracker

A full-stack personal finance tracker built with React/Vite and Spring Boot. **PostgreSQL is the only database.** Features include JWT registration/login, transaction CRUD and filters, CSV export, dashboard totals, spending charts, monthly/category budgets, and responsive navigation.

## Prerequisites
- Java 25 (JDK 25)
- Maven 3.9+ (or use the Maven Wrapper if included)
- Node.js 20 or newer and npm
- Docker Desktop (recommended for PostgreSQL) or a local PostgreSQL 14+ installation
- Maven 3.9+ (or use your IDE's Maven integration)

## 1. Configure environment
Copy `.env.example` to `.env` and replace the placeholder password and JWT secret. Docker Compose reads `.env` automatically. For local Spring Boot runs, export the variables in your terminal or set them in the IDE run configuration. The backend has safe local development defaults, but **replace them before sharing or deploying**.

Generate a JWT secret with a long random value (at least 32 bytes). Example PowerShell:
```powershell
Copy-Item .env.example .env
# Edit .env and set POSTGRES_PASSWORD and JWT_SECRET before running.
```

## 2. Start PostgreSQL
From the project root:
```bash
docker compose up -d postgres
```
This creates database `personal_finance_tracker`, user `finance_user`, and the persistent `finance_data` volume. Set matching values in `.env`.

If you use an existing PostgreSQL installation, create the database and user to match your environment variables. `database/schema.sql` is supplied as a reference schema; Spring JPA `ddl-auto=update` creates/updates tables on first startup.

## 3. Start the backend
Open a terminal in `backend/`:
```bash
mvn spring-boot:run
```
Or open `backend/pom.xml` in IntelliJ IDEA and run `PersonalFinanceTrackerApplication`. The API runs on `http://localhost:8080`.

Swagger UI: `http://localhost:8080/swagger-ui/index.html`
OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 4. Start the frontend
Open another terminal in `frontend/`:
```bash
npm install
npm run dev
```
Open `http://localhost:5173`. If the API runs elsewhere, create `frontend/.env` with `VITE_API_BASE_URL=http://localhost:8080/api` and restart Vite.

## 5. Quick smoke test
1. Register a user with a valid email and password of at least 8 characters.
2. Add an income transaction and an expense transaction.
3. Confirm dashboard totals and charts update.
4. Filter transactions by category/type/date and export CSV.
5. Create a budget and confirm spending progress.
6. Register a second user and verify the first user's transactions are not visible.

## Environment variables
| Variable | Purpose | Default for local development |
|---|---|---|
| `POSTGRES_DB` | Database name | `personal_finance_tracker` |
| `POSTGRES_USER` | Database user | `finance_user` |
| `POSTGRES_PASSWORD` | Database password | `change_me_locally` |
| `DB_HOST` | PostgreSQL host | `localhost` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `SPRING_DATASOURCE_URL` | Optional full JDBC URL override | built from DB settings |
| `SPRING_DATASOURCE_USERNAME` | Optional DB user override | `POSTGRES_USER` |
| `SPRING_DATASOURCE_PASSWORD` | Optional DB password override | `POSTGRES_PASSWORD` |
| `JWT_SECRET` | HMAC JWT signing secret, at least 32 bytes | development-only fallback |
| `JWT_EXPIRATION_MS` | JWT lifetime in milliseconds | `86400000` |
| `FRONTEND_ORIGIN` | Allowed CORS origin | `http://localhost:5173` |
| `VITE_API_BASE_URL` | Frontend API base URL | `http://localhost:8080/api` |

## Tests and builds
Backend tests: `cd backend && mvn test`
Backend package: `cd backend && mvn clean package`
Frontend production build: `cd frontend && npm install && npm run build`

Builds/tests were not executed as part of generating this archive; run the commands above in an environment with Maven and npm dependency access. The application is scaffolded to use PostgreSQL; it does not include MongoDB or an in-memory database.

## Project layout
- `frontend/` React application
- `backend/` Spring Boot REST API and security
- `database/schema.sql` PostgreSQL schema reference
- `docs/API_DOCUMENTATION.md` endpoint guide
- `docs/PROJECT_OVERVIEW.md` requirements and data model
- `.env.example` environment template
- `docker-compose.yml` PostgreSQL service

## Troubleshooting
- **Database connection refused:** confirm `docker compose ps` and port 5432.
- **Password authentication failed:** check `.env` values; changing a password for an already-created Docker volume may require updating the database user's password, not just changing `.env`.
- **JWT secret error:** set `JWT_SECRET` to a random value at least 32 bytes long.
- **CORS error:** set `FRONTEND_ORIGIN` to the exact frontend origin and restart backend.
- **401 response:** log in again; API endpoints other than `/api/auth/**` require a bearer token.
- **Duplicate budget:** one budget is allowed for each user, month, and category.

## Production checklist
Use a secret manager, HTTPS, secure token storage (prefer an HttpOnly Secure SameSite cookie strategy if adapting auth), request throttling, audit-safe logging, database backups, migrations such as Flyway, and security testing. Do not deploy with development secrets or default passwords.
