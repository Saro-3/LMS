import { Link } from "react-router-dom";
import {
  ArrowRight,
  BookOpen,
} from "lucide-react";

import "./Home.css";

function Home() {
  return (
    <div className="home-page">

      <section className="hero-section">

        <div className="hero-container">

          <div className="hero-content">

            <span className="hero-badge">
              Learn. Grow. Achieve.
            </span>

            <h1 className="hero-title">
              Build Skills.
              <br />
              <span>Build Your Future.</span>
            </h1>

            <p className="hero-description">
              Learn practical skills through structured courses,
              interactive lessons, assessments, and industry-focused
              learning experiences.
            </p>

            <div className="hero-actions">

              <Link
                to="/courses"
                className="primary-button"
              >
                Explore Courses
                <ArrowRight size={18} />
              </Link>

              <Link
                to="/register"
                className="secondary-button"
              >
                Get Started
              </Link>

            </div>

          </div>

          <div className="hero-visual">

            <div className="hero-card">

              <div className="hero-card-icon">
                <BookOpen size={34} />
              </div>

              <h3>Start Learning Today</h3>

              <p>
                Access courses, track your progress,
                complete assessments, and earn certificates.
              </p>

              <div className="hero-progress">

                <div className="progress-header">
                  <span>Learning Progress</span>
                  <span>75%</span>
                </div>

                <div className="progress-bar">
                  <div className="progress-value"></div>
                </div>

              </div>

            </div>

          </div>

        </div>

      </section>

    </div>
  );
}

export default Home;