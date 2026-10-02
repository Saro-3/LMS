import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Eye, EyeOff, Lock, Mail, BookOpen } from "lucide-react";
import { toast } from "react-hot-toast";

import api from "../../service/api";
import { useAuth } from "../../context/AuthContext";

import "./Login.css";

function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.email || !formData.password) {
      toast.error("Please enter email and password.");
      return;
    }

    try {
      setLoading(true);

      const response = await api.post(
        "/auth/login",
        formData
      );

      console.log("Login Response:", response.data);

      const data = response.data;

      /*
       * Your backend login response should contain
       * the JWT token and user information.
       */
      const token =
        data.token ||
        data.accessToken ||
        data.jwt;

      const user =
        data.user ||
        data;

      if (!token) {
        toast.error("Login successful but JWT token was not found.");
        return;
      }

      login(token, user);

      toast.success("Login successful!");

      const role =
        user?.role?.name ||
        user?.role ||
        "";

      if (role === "ROLE_ADMIN") {
        navigate("/admin");
      } else if (role === "ROLE_INSTRUCTOR") {
        navigate("/instructor");
      } else {
        navigate("/student");
      }

    } catch (error) {
      console.error("Login error:", error);

      const message =
        error.response?.data?.message ||
        "Invalid email or password.";

      toast.error(message);

    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="login-page">

      <div className="login-container">

        {/* Left Side */}
        <div className="login-info">

          <div className="login-brand">
            <BookOpen size={30} />
            <span>LMS</span>
          </div>

          <h1>
            Continue Your
            <span> Learning Journey</span>
          </h1>

          <p>
            Sign in to access your courses, track your
            progress, complete assessments, and earn
            certificates.
          </p>

          <div className="login-benefits">

            <div>
              <span>✓</span>
              Access your enrolled courses
            </div>

            <div>
              <span>✓</span>
              Track your learning progress
            </div>

            <div>
              <span>✓</span>
              Complete quizzes and assignments
            </div>

            <div>
              <span>✓</span>
              Earn course certificates
            </div>

          </div>

        </div>

        {/* Login Card */}
        <div className="login-card">

          <div className="login-card-header">

            <h2>Welcome Back</h2>

            <p>
              Sign in to your LMS account
            </p>

          </div>

          <form onSubmit={handleSubmit}>

            {/* Email */}
            <div className="login-form-group">

              <label htmlFor="email">
                Email Address
              </label>

              <div className="login-input-wrapper">

                <Mail size={18} />

                <input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="Enter your email"
                  value={formData.email}
                  onChange={handleChange}
                />

              </div>

            </div>

            {/* Password */}
            <div className="login-form-group">

              <label htmlFor="password">
                Password
              </label>

              <div className="login-input-wrapper">

                <Lock size={18} />

                <input
                  id="password"
                  name="password"
                  type={
                    showPassword
                      ? "text"
                      : "password"
                  }
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={handleChange}
                />

                <button
                  type="button"
                  className="password-toggle"
                  onClick={() =>
                    setShowPassword(
                      !showPassword
                    )
                  }
                >
                  {showPassword ? (
                    <EyeOff size={18} />
                  ) : (
                    <Eye size={18} />
                  )}
                </button>

              </div>

            </div>

            {/* Submit */}
            <button
              type="submit"
              className="login-button"
              disabled={loading}
            >
              {loading
                ? "Signing in..."
                : "Sign In"}
            </button>

          </form>

          <div className="login-register">

            Don't have an account?

            <Link to="/register">
              Create an account
            </Link>

          </div>

        </div>

      </div>

    </section>
  );
}

export default Login;