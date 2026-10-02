import { Outlet } from "react-router-dom";

import "./PublicLayout.css";
import Navbar from "./Navbar";
import Footer from "./Footer";

function PublicLayout() {
  return (
    <div className="public-layout">

      <Navbar />

      <main className="public-content">
        <Outlet />
      </main>

      <Footer />

    </div>
  );
}

export default PublicLayout;