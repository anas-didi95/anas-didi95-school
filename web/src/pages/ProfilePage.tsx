import { Component } from "solid-js";

const ProfilePage: Component = () => {
  return (
    <div class="min-h-screen bg-base-200 flex items-center justify-center p-4">
      <div class="card w-full max-w-md bg-base-100 shadow-xl">
        <div class="card-body items-center text-center">
          {/* Avatar */}
          <div class="avatar">
            <div class="w-24 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
              <img src="https://i.pravatar.cc/300" alt="User avatar" />
            </div>
          </div>

          {/* Name */}
          <h2 class="card-title mt-4">Alex Johnson</h2>
          <p class="text-sm text-base-content/70">@alexj</p>

          {/* Bio */}
          <p class="mt-2 text-sm text-base-content/80">
            Frontend developer who loves SolidJS, Tailwind CSS, and clean UI
            design.
          </p>

          {/* Stats */}
          <div class="stats stats-horizontal shadow mt-4">
            <div class="stat">
              <div class="stat-title text-xs">Followers</div>
              <div class="stat-value text-primary">128</div>
            </div>

            <div class="stat">
              <div class="stat-title text-xs">Following</div>
              <div class="stat-value text-primary">87</div>
            </div>

            <div class="stat">
              <div class="stat-title text-xs">Posts</div>
              <div class="stat-value text-primary">42</div>
            </div>
          </div>

          {/* Actions */}
          <div class="card-actions mt-6 w-full gap-2">
            <button class="btn btn-primary w-full">Follow</button>
            <button class="btn btn-outline w-full">Message</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
