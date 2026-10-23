import { useEffect, useState } from "react";
import { getHistory } from "../services/analysisServices";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";

function HistroyPage() {
const [documents, setDocuments] = useState([]);

useEffect(() => {
  async function fetchHistory(){
    const data = await getHistory();
    setDocuments(data);
  }
  fetchHistory();
}, [])

return (
  <>
  <Navbar />
  <div className="flex flex-col items-center min-h-screen bg-surface text-ink p-8">
    <h1 className="text-2xl font-bold mb-4">History</h1>

    <div className="flex flex-col gap-2 w-96">
      {documents.map((doc) => (
        <Link
        key={doc.id}
        to={`/result/${doc.id}`}
        className="bg-card border border-border text-ink p-3 rounded-lg hover:border-accent transition-colors"
        >
          {doc.fileName}
        </Link>
      ))}

    </div>
  </div>
  </>
  );
}
export default HistroyPage;