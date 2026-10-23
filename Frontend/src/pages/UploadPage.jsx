import { useState } from "react";
import { upload } from "../services/analysisServices";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

function UploadPage() {

  const [file, setFile] = useState(null);
  const navigate = useNavigate();

  async function handleSubmit(e){
    e.preventDefault();
    const document = await upload(file);
    navigate(`/result/${document.id}`)
  }

  return (
    <>
    <Navbar />
    <div className="flex items-center justify-center min-h-screen bg-surface text-ink">
      <form onSubmit={handleSubmit} className="flex flex-col gap-4 w-80">
      <h1 className="text-2xl font-bold">Upload Document</h1>

        <input 
        type="file"
        accept=".pdf, .docx"
        onChange={(e) => setFile(e.target.files[0])}
        className="text-sm text-ink file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:font-bold file:bg-accent file:text-surface hover:file:bg-accent-dark file:cursor-pointer"
        />

        <button type="submit" className="bg-accent text-surface font-bold p-2 rounded hover:bg-accent-dark">
          Analyze
        </button>
      </form>
    </div>
    </>
  );
} 

export default UploadPage;