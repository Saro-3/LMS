import { ArrowLeft, Mail, Shield, User, Hash } from "lucide-react";
import { Link } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";

import "./ProfileSettings.css";

function ProfileSettings() {
  const { user } = useAuth();

  const getInitial = () => {
    if (!user?.name) {
      return "U";
    }

    return user.name.charAt(0).toUpperCase();
  };

  const getRoleName = () => {
    switch (user?.role) {
      case "ROLE_ADMIN":
        return "Administrator";

      case "ROLE_INSTRUCTOR":
        return "Instructor";

      case "ROLE_STUDENT":
        return "Student";

      default:
        return "User";
    }
  };

  return (
    <div className="profile-settings-page">

      <div className="profile-settings-container">

        <Link
          to="/"
          className="profile-back-link"
        >
          <ArrowLeft size={18} />
          Back to Home
        </Link>

        <div className="profile-settings-header">

          <div>
            <span className="profile-settings-label">
              ACCOUNT
            </span>

            <h1>Profile Settings</h1>

            <p>
              View your account information and profile details.
            </p>
          </div>

        </div>

        <div className="profile-settings-card">


          <div className="profile-settings-profile">

            <div className="profile-settings-avatar">
              {getInitial()}
            </div>

            <div className="profile-settings-profile-info">

              <h2>
                {user?.name || "User"}
              </h2>

              <p>
                {user?.email || "No email available"}
              </p>

              <span className="profile-role-badge">
                {getRoleName()}
              </span>

            </div>

          </div>

          <div className="profile-settings-divider"></div>


          <div className="profile-section">

            <div className="profile-section-heading">

              <div className="profile-section-icon">
                <User size={19} />
              </div>

              <div>
                <h3>Personal Information</h3>

                <p>
                  Your account information
                </p>
              </div>

            </div>

            <div className="profile-fields">


              <div className="profile-field">

                <label>
                  Full Name
                </label>

                <div className="profile-input-wrapper">

                  <User size={17} />

                  <input
                    type="text"
                    value={user?.name || ""}
                    readOnly
                  />

                </div>

              </div>

              <div className="profile-field">

                <label>
                  Email Address
                </label>

                <div className="profile-input-wrapper">

                  <Mail size={17} />

                  <input
                    type="email"
                    value={user?.email || ""}
                    readOnly
                  />

                </div>

              </div>


              <div className="profile-field">

                <label>
                  Account Role
                </label>

                <div className="profile-input-wrapper">

                  <Shield size={17} />

                  <input
                    type="text"
                    value={getRoleName()}
                    readOnly
                  />

                </div>

              </div>


              <div className="profile-field">

                <label>
                  User ID
                </label>

                <div className="profile-input-wrapper">

                  <Hash size={17} />

                  <input
                    type="text"
                    value={user?.userId || ""}
                    readOnly
                  />

                </div>

              </div>

            </div>

          </div>

          <div className="profile-settings-divider"></div>

          <div className="profile-section">

            <div className="profile-section-heading">

              <div className="profile-section-icon">
                <Shield size={19} />
              </div>

              <div>
                <h3>Account Security</h3>

                <p>
                  Manage your account security
                </p>
              </div>

            </div>

            <div className="security-card">

              <div className="security-card-content">

                <div className="security-icon">
                  <Shield size={20} />
                </div>

                <div>

                  <h4>Password</h4>

                  <p>
                    Your password is securely protected.
                  </p>

                </div>

              </div>

              <button
                type="button"
                className="security-button"
                disabled
              >
                Change Password
              </button>

            </div>

            <p className="profile-coming-soon">
              Password management will be available
              when the account management API is added.
            </p>

          </div>

        </div>

      </div>

    </div>
  );
}

export default ProfileSettings;