<%@ page import="java.util.List" %>
<%@ page import="com.library.model.Book" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Library Books</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background: #f5f5f5;
        }

        h1 {
            margin-bottom: 25px;
        }

        h2 {
            margin-bottom: 15px;
        }

        form {
            background: white;
            padding: 20px;
            margin-bottom: 30px;
        }

        input {
            padding: 10px;
            margin: 5px;
        }

        button {
            padding: 10px 20px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #222;
            color: white;
        }

        tr:hover {
            background: #f1f1f1;
        }

    </style>

</head>

<body>

    <h2>Add New Book</h2>

    <form action="${pageContext.request.contextPath}/books"
          method="post">

        <input
            type="text"
            name="title"
            placeholder="Book Title"
            required>

        <input
            type="text"
            name="isbn"
            placeholder="ISBN"
            required>

        <input
            type="text"
            name="publisher"
            placeholder="Publisher"
            required>

        <input
            type="number"
            name="publicationYear"
            placeholder="Publication Year"
            required>

        <input
            type="number"
            name="totalCopies"
            placeholder="Total Copies"
            min="1"
            required>

        <button type="submit">
            Add Book
        </button>

    </form>

    <h1>Library Books</h1>

    <table>

        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>ISBN</th>
            <th>Publisher</th>
            <th>Year</th>
            <th>Total Copies</th>
            <th>Available Copies</th>
        </tr>

<%
        List<Book> books =
                (List<Book>) request.getAttribute("books");

        if (books != null) {

            for (Book book : books) {
%>

        <tr>

            <td>
                <%= book.getBookId() %>
            </td>

            <td>
                <%= book.getTitle() %>
            </td>

            <td>
                <%= book.getIsbn() %>
            </td>

            <td>
                <%= book.getPublisher() %>
            </td>

            <td>
                <%= book.getPublicationYear() %>
            </td>

            <td>
                <%= book.getTotalCopies() %>
            </td>

            <td>
                <%= book.getAvailableCopies() %>
            </td>

        </tr>

<%
            }
        }
%>

    </table>

</body>

</html>