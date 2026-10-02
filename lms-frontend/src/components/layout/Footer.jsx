import { Link } from "react-router-dom";
import { BookOpen } from "lucide-react";

import "./Footer.css";

function Footer() {
  return (
    <footer className="footer">

      <div className="footer-container">

        <div className="footer-brand">

          <Link to="/" className="footer-logo">
            <BookOpen className="footer-logo-icon" />

            <span>LMS</span>
          </Link>

          <p className="footer-description">
            Learn new skills, improve your knowledge, and
            build your career through practical online learning.
          </p>

        </div>

        <div className="footer-column">

          <h3>Quick Links</h3>

          <Link to="/">Home</Link>
          <Link to="/courses">Courses</Link>
          <Link to="/about">About Us</Link>

        </div>

        <div className="footer-column">

          <h3>Learning</h3>

          <Link to="/courses">Browse Courses</Link>
          <Link to="/register">Join LMS</Link>
          <Link to="/login">Student Login</Link>

        </div>

        <div className="footer-column">

          <h3>Contact</h3>

          <p>support@lms.com</p>
          <p>Online Learning Platform</p>
          <p>Available Worldwide</p>

        </div>

      </div>

      <div className="footer-bottom">

        <p>
          © 2026 LMS. All rights reserved.
        </p>

        <p>
          Learn. Grow. Achieve.
        </p>

      </div>

    </footer>
  );
}

export default Footer;