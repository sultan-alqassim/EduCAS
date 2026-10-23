import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getResult } from "../services/analysisServices";
import Navbar from "../components/Navbar";

function ResultsPage() {
  const { documentId } = useParams();
  const [result, setResult] = useState(null);

  useEffect(() => {
    async function fetchResult() {
      const data = await getResult(documentId);
      setResult(data);
    }
    fetchResult();
  }, [documentId]);

  return (
    <>
    <Navbar />
      <div className="min-h-screen bg-surface text-ink flex items-center justify-center">
        {!result ? (
          <p className="text-ink">Loading...</p>
        ) : (
          <div className="flex flex-col gap-4 w-full max-w-2xl px-6 py-10">
            <div className="flex gap-4">
              <div className="flex-1 bg-card border border-border rounded-xl p-5">
                <div className="flex justify-between items-start mb-2">
                  <span className="text-xs uppercase tracking-wider opacity-60">Primary Domain</span>
                  <span className="text-xs font-bold bg-accent text-surface px-2 py-0.5 rounded-full">{result.confidence}%</span>
                </div>
                <div className="text-xl font-bold">{result.primaryDomain}</div>
              </div>
              <div className="flex-1 bg-card border border-border rounded-xl p-5">
                <span className="text-xs uppercase tracking-wider opacity-60">Secondary Subject</span>
                <div className="text-xl font-bold mt-2">{result.secondarySubject}</div>
              </div>
            </div>
            <div className="flex flex-wrap gap-2">
              {result.keyTopics.split(',').map((topic) => (
                <span key={topic} className="bg-card border border-border rounded-full px-3 py-1 text-xs font-semibold">
                  {topic.trim()}
                </span>
              ))}
            </div>
            <div className="bg-card border border-border rounded-xl p-5">
              <span className="text-xs uppercase tracking-wider opacity-60">Summary</span>
              <p className="text-sm mt-2 leading-relaxed">{result.summary}</p>
            </div>
            <div className="bg-card border border-border rounded-xl p-5">
              <span className="text-xs uppercase tracking-wider opacity-60">Recommendations</span>
              <p className="text-sm mt-2 leading-relaxed">{result.recommendations}</p>
            </div>
          </div>
        )}
      </div>
    </>
  );
}
export default ResultsPage;