import { Link } from "react-router-dom";

function Navbar({ hideUpload }) {
  return (
    <nav className="flex justify-between items-center p-4 border-b border-border bg-surface text-ink">
      <span className="font-bold text-xl">EduCAS</span>
      <div className="flex gap-4">
        {!hideUpload && <Link to="/upload">Upload</Link>}
        <Link to="/history">History</Link>
      </div>
    </nav>
  );
}
export default Navbar;