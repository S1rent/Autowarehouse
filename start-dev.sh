#!/bin/bash

echo "========================================"
echo "   AUTOWAREHOUSE DEVELOPMENT SERVER"
echo "========================================"
echo ""
echo "Starting Frontend and Backend servers..."
echo ""

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "ERROR: Node.js is not installed"
    echo "Please install Node.js from https://nodejs.org/"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed"
    echo "Please install Apache Maven"
    exit 1
fi

echo "All prerequisites are installed!"
echo ""

# Function to cleanup background processes
cleanup() {
    echo ""
    echo "Stopping servers..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    exit 0
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Start Backend
echo "Starting Backend Server (Quarkus)..."
cd autowarehouse-backend
mvn quarkus:dev &
BACKEND_PID=$!
cd ..

# Wait for backend to start
echo "Waiting for backend to start..."
sleep 10

# Start Frontend
echo "Starting Frontend Server (Vite)..."
cd autowarehouse-frontend
npm run dev &
FRONTEND_PID=$!
cd ..

echo ""
echo "========================================"
echo "   SERVERS RUNNING"
echo "========================================"
echo ""
echo "Backend:  http://localhost:8080"
echo "Frontend: http://localhost:5173"
echo "API Docs: http://localhost:8080/q/swagger-ui"
echo ""
echo "Press Ctrl+C to stop both servers"
echo ""

# Wait for processes to finish
wait $BACKEND_PID $FRONTEND_PID
