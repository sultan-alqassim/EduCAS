import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

function WelcomePage() {
  const navigate = useNavigate();
  return (
    <>
      <Navbar hideUpload={true} />
      <div className="min-h-screen bg-surface text-ink px-12 flex flex-col justify-center">
        <div className="max-w-2xl mx-auto text-center">
          <p className="text-xs font-bold uppercase tracking-wider text-accent mb-3">
            Educational Content Analysis
          </p>
          <h1 className="text-3xl font-bold mb-4">
            understand any document instantly.
          </h1>
          <p className="text-sm opacity-75 mb-6 leading-relaxed">
            Upload a PDF or DOCX and EduCAS reads it for you, classifying the subject, pulling out the key topics, and giving you a summary and recommendations to study further.
          </p>
          <button
           onClick={() => navigate('/upload')}
           className="bg-accent text-surface font-bold px-6 py-3 rounded-lg hover:bg-accent-dark"
           >
            Get Started 
          </button>
        </div>
        <div className="flex gap-4 mt-10">
          <div className="flex-1 bg-card border border-border rounded-xl p-5">
            <h3 className="font-bold mb-2">Fast classification</h3>
            <p className="text-xs opacity-70 leading-relaxed">
              Get the primary domain and subject of your document in seconds, no reading required.
            </p>
          </div>
          <div className="flex-1 bg-card border border-border rounded-xl p-5">
            <h3 className="font-bold mb-2">Key Topic Extraction</h3>
            <p className="text-xs opacity-70 leading-relaxed">
              See exactly which topics the document covers, pulled out automatically for you.
            </p>
          </div>
          <div className="flex-1 bg-card border border-border rounded-xl p-5">
            <h3 className="font-bold mb-2">Smart Recommendations</h3>
            <p className="text-xs opacity-70 leading-relaxed">
              Get a short summary plus suggestions on what to study next based on the content.
            </p>
          </div>
        </div>
      </div>
    </>
  );
}
export default WelcomePage;