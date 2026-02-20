import { NavLink } from "react-router-dom";
import { Button } from "./button";

export default function Navbar() {
  return (
    <nav className="py-5 border-b border-gray-700 md:py-0 flex md:flex-row gap-4 md:gap-0 flex-col md:h-14 justify-around items-center ">
      {/* Logo */}
      <div className="font-semibold items-center flex gap-2">
        <span className="inline-block h-6 w-6 text-center rounded-md bg-gradient-to-r from-primary to-primary/40">
          A
        </span>

        <NavLink to="/" className="text-base tracking-tight">
          Auth App
        </NavLink>
      </div>

      {/* Links */}
      <div className="flex gap-4 items-center">
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? "text-primary font-semibold" : "text-gray-300"
          }
        >
          Home
        </NavLink>

        <NavLink to="/login">
          <Button size="sm" variant="outline">
            Login
          </Button>
        </NavLink>

        <NavLink to="/signup">
          <Button size="sm" variant="outline">
            Signup
          </Button>
        </NavLink>
      </div>
    </nav>
  );
}
