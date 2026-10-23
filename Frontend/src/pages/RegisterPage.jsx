import { useState } from "react";
import { register } from "../services/authService";
import { Link, useNavigate } from "react-router-dom";

function RegisterPage() {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] =useState('');
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    await register(fullName, email, password);
    navigate('/welcome');
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-surface text-ink gap-4">
      <form onSubmit={handleSubmit} className="flex flex-col gap-4 w-80">
        <h1 className="text-2xl font-bold">register</h1>

        <input 
          type="text"
          placeholder="Full Name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
          className="bg-card border border-border text-ink placeholder-ink/50 p-2 rounded focus:outline-none focus:border-accent"
        />

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
          register
        </button>
      </form>
      <p className="text-ink text-sm">
        Already have an account?{' '} 
        <Link to="/login" className="text-accent underline hover:text-accent-dark">
        Login
        </Link>
      </p>
    </div>
  )
}

export default RegisterPage;