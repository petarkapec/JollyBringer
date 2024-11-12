import NextAuth from 'next-auth';
import GoogleProvider from 'next-auth/providers/google';
import GitHubProvider from 'next-auth/providers/github';
import { Client } from 'pg';

const client = new Client({
  connectionString: process.env.DATABASE_URL,
});

client.connect();

export default NextAuth({
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    }),
    GitHubProvider({
      clientId: process.env.GITHUB_CLIENT_ID,
      clientSecret: process.env.GITHUB_CLIENT_SECRET,
    }),
  ],
  secret: process.env.NEXTAUTH_SECRET,
  events: {
    signIn: async ({ user, account }) => {
      try {
        const query = `
            INSERT INTO "USER" (email, username, provider, role_id)
            VALUES ($1, $2, $3, $4)
                ON CONFLICT (email) DO NOTHING
        `;
        const values = [user.email, user.name, account.provider, 1]; // Assuming role_id 1 for default role

        await client.query(query, values);
      } catch (error) {
        console.error('Error saving user to database:', error);
      }
    },
  },
});