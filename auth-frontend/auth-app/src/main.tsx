import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login.tsx";
import Signup from "./pages/Signup.tsx";
import Service from "./pages/Service.tsx";
import RootLayout from "./pages/RootLayout";
import About from './pages/About.';
import FuturisticLoginPage from "./components/home/FuturisticLoginPage.tsx";
import FuturisticSignUpPage from "./components/home/FuturisticSignUpPage.tsx";

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<RootLayout />}>
        <Route index element={<App />} />
        <Route path="login" element={<FuturisticLoginPage />} />
        <Route path="signup" element={<FuturisticSignUpPage />} />
        <Route path="about" element={<About />} />
        <Route path="service" element={<Service />} />
      </Route>
    </Routes>
  </BrowserRouter>,
);
