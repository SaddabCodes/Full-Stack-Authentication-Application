import { Button } from "./button";

export default function Navbar() {
  return (
    <nav className="flex h-14 justify-around items-center dark:bg-gray-900">
      <div className="font-semibold items-center flex gap-2">
        <span className="inline-block h-6 w-6 text-center rounded-md bg-gradient-to-r  from-primary to-primary/40">
          {"A"}
        </span>
        <span className="text-base tracking-tight">Auth App</span>
      </div>

      <div className="flex gap-4 items-center">
        <a href="#!">Home</a>
        <Button size={"sm"} className="cursor-pointer" variant={"outline"}>
          Login
        </Button>
        <Button size={"sm"} className="cursor-pointer" variant={"outline"}>
          Signup
        </Button>
      </div>
    </nav>
  );
}
