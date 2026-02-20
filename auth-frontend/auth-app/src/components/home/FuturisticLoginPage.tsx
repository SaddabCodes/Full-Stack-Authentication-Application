import { useState } from "react";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { motion } from "framer-motion";
import { Github } from "lucide-react";

export default function FuturisticLoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log({ email, password });
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-black via-gray-900 to-gray-800 px-4">
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="w-full max-w-md"
      >
        <div className="rounded-2xl border border-gray-800 bg-gray-950/80 backdrop-blur-xl shadow-2xl p-6">
          <div className="text-center space-y-2 mb-6">
            <h2 className="text-3xl font-bold bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent">
              Welcome Back
            </h2>
            <p className="text-gray-400 text-sm">
              Securely access your account and continue building.
            </p>
          </div>

          <div className="space-y-6">
            {/* Email & Password Form */}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <label htmlFor="email" className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70 text-gray-300">
                  Email
                </label>
                <Input
                  id="email"
                  type="email"
                  placeholder="you@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="bg-gray-900 border-gray-800 focus-visible:ring-cyan-500"
                />
              </div>

              <div className="space-y-2">
                <label htmlFor="password" className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70 text-gray-300">
                  Password
                </label>
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  className="bg-gray-900 border-gray-800 focus-visible:ring-cyan-500"
                />
              </div>

              <Button
                type="submit"
                className="w-full rounded-xl bg-gradient-to-r from-cyan-500 to-blue-600 hover:opacity-90"
              >
                Login
              </Button>
            </form>

            {/* Divider */}
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <span className="w-full border-t border-gray-800" />
              </div>
              <div className="relative flex justify-center text-xs uppercase">
                <span className="bg-gray-950 px-2 text-gray-500">
                  Or continue with
                </span>
              </div>
            </div>

            {/* Social Login */}
            <div className="grid gap-3">
              <Button
                variant="outline"
                className="w-full border-gray-800 bg-gray-900 hover:bg-gray-800"
              >
                <img
                  src="https://www.svgrepo.com/show/475656/google-color.svg"
                  alt="Google"
                  className="h-4 w-4 mr-2"
                />
                Continue with Google
              </Button>

              <Button
                variant="outline"
                className="w-full border-gray-800 bg-gray-900 hover:bg-gray-800"
              >
                <Github className="h-4 w-4 mr-2" />
                Continue with GitHub
              </Button>
            </div>
          </div>
        </div>
      </motion.div>
    </div>
  );
}
