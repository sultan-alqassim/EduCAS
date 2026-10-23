export async function upload(file){
  const token = localStorage.getItem('token');

  const formData = new FormData();
  formData.append('file', file);

  const response = await fetch('/api/analysis/upload', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
    body: formData,
  });

  return await response.json();
}

export async function getResult(documentId) {
  const token = localStorage.getItem('token');

  const response = await fetch(`/api/analysis/result/${documentId}`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  return await response.json();
}

export async function getHistory() {
  const token = localStorage.getItem('token');

  const response = await fetch('/api/analysis/history', {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${token}`
    },
  });
  return await response.json();
}