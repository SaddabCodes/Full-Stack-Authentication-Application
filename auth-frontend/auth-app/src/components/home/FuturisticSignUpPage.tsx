import { useState } from "react";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { motion } from "framer-motion";

export default function FuturisticSignUpPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log({ name, email, password });
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
            <h2 className="text-3xl font-bold bg-gradient-to-r from-purple-400 to-cyan-500 bg-clip-text text-transparent">
              Create Account
            </h2>
            <p className="text-gray-400 text-sm">
              Join us and secure your digital experience.
            </p>
          </div>

          <div className="space-y-6">
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <label htmlFor="signup-name" className="text-sm font-medium leading-none text-gray-300">
                  Full Name
                </label>
                <Input
                  id="signup-name"
                  type="text"
                  placeholder="John Doe"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                  className="bg-gray-900 border-gray-800 focus-visible:ring-cyan-500"
                />
              </div>

              <div className="space-y-2">
                <label htmlFor="signup-email" className="text-sm font-medium leading-none text-gray-300">
                  Email
                </label>
                <Input
                  id="signup-email"
                  type="email"
                  placeholder="you@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="bg-gray-900 border-gray-800 focus-visible:ring-cyan-500"
                />
              </div>

              <div className="space-y-2">
                <label htmlFor="signup-password" className="text-sm font-medium leading-none text-gray-300">
                  Password
                </label>
                <Input
                  id="signup-password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  className="bg-gray-900 border-gray-800 focus-visible:ring-cyan-500"
                />
              </div>

              <Button type="submit" className="w-full rounded-xl bg-gradient-to-r from-purple-500 to-cyan-600 hover:opacity-90">
                Sign Up
              </Button>
            </form>
          </div>
        </div>
      </motion.div>
    </div>
  );
}
