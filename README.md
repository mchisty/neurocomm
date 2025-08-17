# Card Management System

A secure full-stack application for managing card information with AES GCM encryption.

## Features

- **Secure Card Creation**: Add cards with cardholder name and PAN
- **Encrypted Storage**: All PANs are encrypted using AES GCM with random IVs
- **Search Functionality**: Search by full PAN or last 4 digits
- **Masked Display**: PANs are never displayed in plain text
- **Luhn Validation**: Automatic card number validation

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.2.0, H2 Database
- **Frontend**: React 18, Axios

## Quick Start

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend-react
npm install
npm start
```

## API Endpoints

- `POST /api/cards` - Create a new card
- `GET /api/cards/search/pan?pan={pan}` - Search by full PAN
- `GET /api/cards/search/last-four?lastFourDigits={digits}` - Search by last 4 digits

## Security

- AES GCM encryption with random IVs
- No plaintext PAN storage or logging
- Masked display only
- Luhn algorithm validation