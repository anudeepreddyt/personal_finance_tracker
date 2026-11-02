# Personal Finance Tracker — project overview

## Goal
A responsive application to record and categorize income and expenses, inspect spending charts, create monthly budgets, and securely view personal financial data.

## Technology
- Frontend: React + Vite, Tailwind CSS, React Router, Axios, Context API, Recharts, React Hot Toast, Lucide React.
- Backend: Java 25, Spring Boot 3, Spring Web, Spring Data JPA, Spring Security, JWT, Bean Validation, Maven, Springdoc OpenAPI.
- Database: PostgreSQL only.

## Use cases
1. Register and log in securely.
2. Add income and expense records.
3. View totals and charts.
4. Update or delete transactions.
5. Filter by type, category, and date.
6. Review history and export CSV.
7. Manage overall and category budgets.
8. Use the dashboard on desktop and mobile.
9. Log out and keep private records scoped to the authenticated user.

## Data model
`app_users` has a generated ID and unique username/email. `finance_transactions` references an owner, amount, type, category, date, and optional notes. `budgets` references an owner, month, category, and limit. Amounts use PostgreSQL `NUMERIC(14,2)` and Java `BigDecimal`.

## Important scope notes
This is a practical starter project, not a security-audited financial product. Review token storage, rate limiting, deployment secrets, logging, and operational controls before production deployment. PDF export, email reports, recurring transactions, and PWA features are not implemented in this version.
