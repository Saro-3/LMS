import { BrowserRouter, Routes, Route } from "react-router-dom";
import PublicLayout from "./components/layout/PublicLayout";
import Home from "./pages/public/Home";
import About from "./pages/public/About";
import Courses from "./pages/public/Courses";
import Login from "./pages/auth/Login";
import CourseDetails from "./pages/public/CourseDetails";
import ProfileSettings from "./pages/common/ProfileSettings";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<PublicLayout />}>

        <Route path="/" element={<Home />} />

          <Route path="/about" element={<About />} />

          <Route path="/courses" element={<Courses />} />

          <Route path="/courses/:courseId" element={<CourseDetails />} />

          <Route path="/login" element={<Login />} />

          <Route path="/profile" element={<ProfileSettings />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;