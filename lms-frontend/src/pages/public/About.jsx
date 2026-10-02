import { Link } from "react-router-dom";
import {
  BookOpen,
  Target,
  Users,
  Award,
  ArrowRight,
  CheckCircle,
} from "lucide-react";

import "./About.css";

function About() {
  return (
    <div className="about-page">

      {/* Hero */}
      <section className="about-hero">
        <div className="about-hero-container">

          <span className="about-label">
            About Our Platform
          </span>

          <h1>
            Empowering People Through
            <span> Better Learning</span>
          </h1>

          <p>
            Our Learning Management System provides a structured
            environment where students can learn new skills,
            track their progress, complete assessments, and
            achieve their learning goals.
          </p>

        </div>
      </section>


      {/* Mission */}
      <section className="about-mission">

        <div className="about-section-container">

          <div className="about-mission-content">

            <span className="about-label">
              Our Mission
            </span>

            <h2>
              Making Learning Simple, Structured, and Accessible
            </h2>

            <p>
              We believe learning should be accessible, organized,
              and focused on practical skill development.
            </p>

            <p>
              Our platform brings courses, lessons, assessments,
              progress tracking, and certifications together in
              one learning experience.
            </p>

          </div>

          <div className="about-mission-card">

            <BookOpen size={42} />

            <h3>
              Learn With Purpose
            </h3>

            <p>
              Build knowledge step by step and turn learning
              into practical skills.
            </p>

          </div>

        </div>

      </section>


      {/* What We Provide */}
      <section className="about-features">

        <div className="about-section-container">

          <div className="about-heading">

            <span className="about-label">
              What We Provide
            </span>

            <h2>
              Everything You Need for Your Learning Journey
            </h2>

            <p>
              Our platform brings the essential parts of online
              learning together in one place.
            </p>

          </div>


          <div className="about-feature-grid">

            <div className="about-feature-card">

              <div className="about-feature-icon">
                <BookOpen />
              </div>

              <h3>Quality Courses</h3>

              <p>
                Explore structured courses covering different
                technical and professional learning areas.
              </p>

            </div>


            <div className="about-feature-card">

              <div className="about-feature-icon">
                <Target />
              </div>

              <h3>Focused Learning</h3>

              <p>
                Follow organized modules and lessons to maintain
                a clear learning path.
              </p>

            </div>


            <div className="about-feature-card">

              <div className="about-feature-icon">
                <Users />
              </div>

              <h3>Instructor Support</h3>

              <p>
                Learn through courses created and managed by
                instructors on the platform.
              </p>

            </div>


            <div className="about-feature-card">

              <div className="about-feature-icon">
                <Award />
              </div>

              <h3>Certification</h3>

              <p>
                Complete your course requirements and receive
                a certificate of completion.
              </p>

            </div>

          </div>

        </div>

      </section>


      {/* Learning Experience */}
      <section className="learning-experience">

        <div className="about-section-container">

          <div className="experience-grid">

            <div className="experience-content">

              <span className="about-label">
                Learning Experience
              </span>

              <h2>
                Learn at Your Own Pace
              </h2>

              <p>
                Our LMS is designed around a simple learning
                journey. Students can enroll in courses, access
                lessons, complete assessments, and monitor their
                progress.
              </p>

              <div className="experience-list">

                <div>
                  <CheckCircle />
                  <span>Structured course modules</span>
                </div>

                <div>
                  <CheckCircle />
                  <span>Lesson progress tracking</span>
                </div>

                <div>
                  <CheckCircle />
                  <span>Quizzes and assessments</span>
                </div>

                <div>
                  <CheckCircle />
                  <span>Assignment evaluation</span>
                </div>

              </div>

            </div>


            <div className="experience-card">

              <div className="experience-card-icon">
                <Target />
              </div>

              <h3>
                Your Learning Journey
              </h3>

              <div className="journey-step">
                <span>01</span>
                <p>Choose a course</p>
              </div>

              <div className="journey-step">
                <span>02</span>
                <p>Learn through lessons</p>
              </div>

              <div className="journey-step">
                <span>03</span>
                <p>Complete assessments</p>
              </div>

              <div className="journey-step">
                <span>04</span>
                <p>Earn your certificate</p>
              </div>

            </div>

          </div>

        </div>

      </section>


      {/* CTA */}
      <section className="about-cta">

        <div className="about-cta-container">

          <h2>
            Start Your Learning Journey
          </h2>

          <p>
            Explore our courses and begin building your skills today.
          </p>

          <Link
            to="/courses"
            className="about-cta-button"
          >
            Explore Courses
            <ArrowRight size={18} />
          </Link>

        </div>

      </section>

    </div>
  );
}

export default About;