import { useEffect, useRef, useState } from "react";
import {
  BookOpen,
  ChevronDown,
  LayoutDashboard,
  Settings,
  LogOut,
} from "lucide-react";
import { Link, useNavigate } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";

import "./Navbar.css";

function Navbar() {
  const { user, isAuthenticated, logout } = useAuth();

  const navigate = useNavigate();

  const [profileOpen, setProfileOpen] = useState(false);

  const profileRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        profileRef.current &&
        !profileRef.current.contains(event.target)
      ) {
        setProfileOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener(
        "mousedown",
        handleClickOutside
      );
    };
  }, []);

  const getInitial = () => {
    if (!user?.name) {
      return "U";
    }

    return user.name.charAt(0).toUpperCase();
  };

  const getDashboardPath = () => {
    switch (user?.role) {
      case "ROLE_ADMIN":
        return "/admin";

      case "ROLE_INSTRUCTOR":
        return "/instructor";

      case "ROLE_STUDENT":
        return "/student";

      default:
        return "/";
    }
  };

  const getDashboardName = () => {
    switch (user?.role) {
      case "ROLE_ADMIN":
        return "Admin Dashboard";

      case "ROLE_INSTRUCTOR":
        return "Instructor Dashboard";

      case "ROLE_STUDENT":
        return "Student Dashboard";

      default:
        return "Dashboard";
    }
  };

  const handleLogout = () => {
    setProfileOpen(false);

    logout();

    navigate("/");
  };

  return (
    <header className="navbar">
      <div className="navbar-container">

        <Link to="/" className="navbar-logo">
          <BookOpen className="navbar-logo-icon" />

          <span className="navbar-logo-text">
            LMS
          </span>
        </Link>


        <nav className="navbar-links">

          <Link
            to="/"
            className="navbar-link"
          >
            Home
          </Link>

          <Link
            to="/courses"
            className="navbar-link"
          >
            Courses
          </Link>

          <Link
            to="/about"
            className="navbar-link"
          >
            About Us
          </Link>

        </nav>

        <div className="navbar-actions">

          {!isAuthenticated ? (
            <>
              <Link
                to="/login"
                className="navbar-login"
              >
                Login
              </Link>

              <Link
                to="/register"
                className="navbar-register"
              >
                Get Started
              </Link>
            </>
          ) : (
            <div
              className="profile-wrapper"
              ref={profileRef}
            >

              <button
                type="button"
                className="profile-button"
                onClick={() =>
                  setProfileOpen(!profileOpen)
                }
                aria-expanded={profileOpen}
              >

                <div className="profile-avatar">
                  {getInitial()}
                </div>

                <div className="profile-user-info">

                  <span className="profile-user-name">
                    {user?.name || "User"}
                  </span>

                  <span className="profile-user-role">
                    {user?.role
                      ?.replace("ROLE_", "")
                      .toLowerCase()
                      .replace(
                        /^\w/,
                        (letter) =>
                          letter.toUpperCase()
                      )}
                  </span>

                </div>

                <ChevronDown
                  className={`profile-chevron ${
                    profileOpen
                      ? "profile-chevron-open"
                      : ""
                  }`}
                  size={17}
                />

              </button>


              {profileOpen && (
                <div className="profile-dropdown">


                  <div className="profile-dropdown-header">

                    <div className="profile-dropdown-avatar">
                      {getInitial()}
                    </div>

                    <div className="profile-dropdown-user">

                      <strong>
                        {user?.name || "User"}
                      </strong>

                      <span>
                        {user?.email || ""}
                      </span>

                    </div>

                  </div>

                  <div className="profile-dropdown-divider"></div>


                  <Link
                    to={getDashboardPath()}
                    className="profile-menu-item"
                    onClick={() =>
                      setProfileOpen(false)
                    }
                  >
                    <LayoutDashboard size={18} />

                    <span>
                      {getDashboardName()}
                    </span>
                  </Link>

                  <Link
                    to="/profile"
                    className="profile-menu-item"
                    onClick={() =>
                      setProfileOpen(false)
                    }
                  >
                    <Settings size={18} />

                    <span>
                      Profile Settings
                    </span>
                  </Link>

                  <div className="profile-dropdown-divider"></div>


                  <button
                    type="button"
                    className="profile-menu-item profile-logout"
                    onClick={handleLogout}
                  >
                    <LogOut size={18} />

                    <span>
                      Logout
                    </span>
                  </button>

                </div>
              )}

            </div>
          )}

        </div>

      </div>
    </header>
  );
}

export default Navbar;