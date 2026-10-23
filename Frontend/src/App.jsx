import { Route, Routes } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import UploadPage from './pages/UploadPage'
import ResultsPage from './pages/ResultsPage'
import HistroyPage from './pages/HistoryPage'
import WelcomePage from './pages/WelcomePage'
import ProtectedRoute from './components/ProtectedRoute'

function App() {

  return (
    <Routes>
      <Route path="/" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route 
      path="/welcome" 
      element={
      <ProtectedRoute>
        <WelcomePage />
      </ProtectedRoute>
      } 
      />

      <Route
        path="/upload" 
        element={
        <ProtectedRoute>
        <UploadPage />
        </ProtectedRoute>
        } 
       />

      <Route
        path="/result/:documentId"
          element={
          <ProtectedRoute>
          <ResultsPage />
          </ProtectedRoute>
          } 
        />

      <Route
        path="/history"
        element={
        <ProtectedRoute>
        <HistroyPage />
        </ProtectedRoute>
        } 
       />
    </Routes>
  )
}

export default App
