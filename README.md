# EduCAS Educational Content Analysis System
EduCAS Reads Your PDF and DOCX documents and classifies them -> Primary Domain, Secondary Subject, key Topics, a Summary, and Study Recommendations, Using Google Gemini. 

## Features
- User authentication with JWT (Register/Login)
- Upload PDF or DOCX documents for analysis
- AI-Powered classification: Primary Domain, Secondary Subject, and Confidence Score 
- Automatic Key Topic Extraction
- Document summary and personalized study recommendations
- Per-user Document History

## Tech Stack
**Backend:** Java 21, Spring Boot, Spring Security (JWT), Spring Data JPA, MySQL, Apache PDFBox, Apache POI, Google gemini

**Frontend:** React, Vite, Tailwind CSS, React Router

## Getting Started

### Prerequisites
- Java 21
- Node.js
- MySQL

### Backend Setup
1. Create a MySQL database named `educas`
2. Copy `Backend/src/main/resources/application.properties.example` to `Backend/src/main/resources/application.properties` Fill Your Own Values (MySQL credentials, JWT secret, Gemini API key)
3. Run The Backend:
```
cd Backend
./mvnw spring-boot:run
```
  the Backend runs on `http://localhost:8080`


### Frontend Setup
1. install dependencies and run the dev server:
```
cd Frontend
npm install
npm run dev
```
  the frontend runs on `http://localhost:5173`

## Status

This project is currently run locally (no live deployment yet). Deployment is planned as a next step.

