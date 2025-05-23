<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rss="http://univ.fr/rss25">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!-- Root template -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Liste des articles RSS</title>
                <link rel="stylesheet" href="/css/style.css"/>
                <style>
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px;
                        background-color: white;
                        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
                    }
                    th, td {
                        padding: 12px 15px;
                        text-align: left;
                        border-bottom: 1px solid #ddd;
                    }
                    th {
                        background-color: #4CAF50;
                        color: white;
                    }
                    tr:hover {
                        background-color: #f5f5f5;
                    }
                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .feed-info {
                        background-color: #f8f9fa;
                        padding: 15px;
                        margin: 15px 0;
                        border-radius: 5px;
                        border-left: 4px solid #4CAF50;
                    }
                    .feed-info h2 {
                        margin-top: 0;
                        color: #333;
                    }
                    .category {
                        display: inline-block;
                        background-color: #e9ecef;
                        padding: 3px 8px;
                        border-radius: 3px;
                        margin-right: 5px;
                        font-size: 0.9em;
                    }
                    .btn-view {
                        background-color: #2196F3;
                        color: white;
                        padding: 6px 12px;
                        border-radius: 4px;
                        text-decoration: none;
                        display: inline-block;
                    }
                    .btn-view:hover {
                        background-color: #0b7dda;
                    }
                    hr {
                        margin: 30px 0;
                        border: 0;
                        border-top: 1px solid #ddd;
                    }
                </style>
            </head>
            <body>
                <!-- Navbar -->
                <nav>
                    <ul>
                        <li><a href="/">Accueil</a></li>
                        <li><a href="/rss25SB/resume/xml">Xml</a></li>
                        <li><a href="/rss25SB/resume/html">Html</a></li>
                        <li><a href="/insert">Insertion</a></li>
                        <li><a href="/items">Articles</a></li>
                        <li><a href="/help">Help</a></li>
                    </ul>
                </nav>

                <div class="container">
                    <h1>Liste des articles RSS</h1>
                    <xsl:for-each select="//rss:feed">
                        <div class="feed-info">
                            <h2><xsl:value-of select="rss:title"/></h2>
                            <p><strong>Date de publication:</strong> <xsl:value-of select="rss:pubDate"/></p>
                            <p><strong>Copyright:</strong> <xsl:value-of select="rss:copyright"/></p>
                            <xsl:if test="@lang">
                                <p><strong>Langue:</strong> <xsl:value-of select="@lang"/></p>
                            </xsl:if>
                        </div>

                        <h3>Articles dans ce flux</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th>Titre</th>
                                    <th>Date</th>
                                    <th>Catégories</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:apply-templates select="rss:item"/>
                            </tbody>
                        </table>
                        <hr/>
                    </xsl:for-each>
                </div>

                <footer>
                    <p>© 2025 Service REST RSS25. Tous droits réservés.</p>
                </footer>
            </body>
        </html>
    </xsl:template>

    <!-- Template for each item -->
    <xsl:template match="rss:item">
        <tr>
            <td>
                <xsl:value-of select="rss:title"/>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="rss:published">
                        <xsl:value-of select="rss:published"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rss:updated"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:for-each select="rss:category">
                    <span class="category"><xsl:value-of select="@term"/></span>
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each>
            </td>
            <td>
                <a href="/rss25SB/html/guid/{rss:guid}" class="btn btn-view">Voir</a>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
