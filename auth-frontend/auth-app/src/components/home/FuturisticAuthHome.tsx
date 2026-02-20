import { Button } from "../ui/button";
import { ShieldCheck, Lock, Zap, UserCheck } from "lucide-react";
import { motion } from "framer-motion";

function FuturisticAuthHome() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-black via-gray-900 to-gray-800 text-white">
      {/* Hero Section */}
      <section className="flex flex-col items-center justify-center text-center px-6 py-28">
        <motion.h1
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          className="text-5xl md:text-6xl font-bold mb-6 bg-gradient-to-r
           from-cyan-400 to-blue-500 bg-clip-text text-transparent"
        >
          Secure. Fast. Reliable Authentication.
        </motion.h1>
        <p className="text-gray-300 max-w-2xl text-lg mb-8">
          A modern authentication platform built with security-first principles,
          lightning-fast performance, and seamless user experience.
        </p>
        <div className="flex gap-4">
          <Button className="px-8 py-6 text-lg rounded-2xl">Get Started</Button>
          <Button variant="outline" className="px-8 py-6 text-lg rounded-2xl">
            Learn More
          </Button>
        </div>
      </section>

      {/* Features Section */}
      <section className="px-6 py-20 max-w-6xl mx-auto">
        <h2 className="text-4xl font-semibold text-center mb-14">
          Powerful Features
        </h2>
        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
          <FeatureCard
            icon={<ShieldCheck size={32} />}
            title="Advanced Security"
            description="JWT, OAuth2, encrypted passwords, and multi-factor authentication support."
          />
          <FeatureCard
            icon={<Zap size={32} />}
            title="High Performance"
            description="Optimized backend with minimal latency and scalable architecture."
          />
          <FeatureCard
            icon={<Lock size={32} />}
            title="Role-Based Access"
            description="Fine-grained authorization with customizable user roles."
          />
          <FeatureCard
            icon={<UserCheck size={32} />}
            title="Social Login"
            description="Login with Google, GitHub, and other OAuth providers."
          />
        </div>
      </section>

      {/* How It Works */}
      <section className="bg-gray-900 py-20 px-6">
        <div className="max-w-5xl mx-auto text-center">
          <h2 className="text-4xl font-semibold mb-12">How It Works</h2>
          <div className="grid md:grid-cols-3 gap-10">
            <Step
              number="01"
              title="User Signup"
              description="Users register securely with encrypted password storage."
            />
            <Step
              number="02"
              title="Authentication"
              description="Secure login generates JWT tokens for session management."
            />
            <Step
              number="03"
              title="Authorization"
              description="Access control ensures users access only permitted resources."
            />
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-24 px-6 text-center bg-gradient-to-r from-blue-600 to-cyan-500">
        <h2 className="text-4xl font-bold mb-6">
          Ready to Secure Your Application?
        </h2>
        <p className="mb-8 text-lg">
          Integrate powerful authentication into your system in minutes.
        </p>
        <Button className="px-10 py-6 text-lg rounded-2xl bg-black text-white hover:bg-gray-900">
          Start Now
        </Button>
      </section>

      {/* Footer */}
      <footer className="bg-black py-8 text-center text-gray-400 text-sm">
        © {new Date().getFullYear()} AuthApp. All rights reserved.
      </footer>
    </div>
  );
}

interface FeatureCardProps {
  icon: React.ReactNode;
  title: string;
  description: string;
}

function FeatureCard({ icon, title, description }: FeatureCardProps) {
  return (
    <div className="bg-gray-800 border border-gray-700 rounded-2xl shadow-lg hover:scale-105 transition-transform">
      <div className="p-6 flex flex-col items-center text-center gap-4">
        <div className="text-cyan-400">{icon}</div>
        <h3 className="text-xl font-semibold">{title}</h3>
        <p className="text-gray-400 text-sm">{description}</p>
      </div>
    </div>
  );
}

interface StepProps {
  number: string;
  title: string;
  description: string;
}

function Step({ number, title, description }: StepProps) {
  return (
    <div className="flex flex-col items-center text-center gap-4">
      <div className="text-4xl font-bold text-cyan-400">{number}</div>
      <h3 className="text-xl font-semibold">{title}</h3>
      <p className="text-gray-400 text-sm max-w-xs">{description}</p>
    </div>
  );
}

export { FuturisticAuthHome, FeatureCard, Step };
