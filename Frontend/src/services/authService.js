export async function login(email, password) {
  const response = await fetch('/api/auth/login', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({email, password})
  });
  
  const token = await response.text();
  localStorage.setItem('token', token);
  return token;
}

export async function register(fullName, email, password) {
  const response = await fetch('/api/auth/register', {
    method:'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({fullName, email, password}),
  });

  const token = await response.text();
  localStorage.setItem('token', token)
  return token;
}