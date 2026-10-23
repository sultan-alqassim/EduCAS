import { useState } from "react";
import { login } from "../services/authService";
import { Link, useNavigate } from "react-router-dom";
function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    await login(email, password);
    navigate('/welcome');
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-surface text-ink gap-4">
      <form onSubmit={handleSubmit} className="flex flex-col gap-4 w-80">
        <h1 className="text-2xl font-bold">Login</h1>

        <input 
          type="email"
          placeholder="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="bg-card border border-border text-ink placeholder-ink/50 p-2 rounded focus:outline-none focus:border-accent"
        />

        <input 
          type="password"
          placeholder="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="bg-card border border-border text-ink placeholder-ink/50 p-2 rounded focus:outline-none focus:border-accent"
        />

        <button type="submit" className="bg-accent text-surface font-bold p-2 rounded hover:bg-accent-dark">
          Login
        </button>
      </form>
      <p className="text-ink text-sm">
        Don't have an account?{' '} 
        <Link to="/register" className="text-accent underline hover:text-accent-dark">
        Register
        </Link>
        </p>
    </div>
  );
}

export default LoginPage;