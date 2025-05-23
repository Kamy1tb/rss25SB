<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rss="http://univ.fr/rss25">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!-- Root template -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Détail de l'article RSS</title>
                <link rel="stylesheet" href="/css/style.css"/>
                <style>
                    .container {
                        max-width: 800px;
                        margin: 0 auto;
                        background-color: white;
                        padding: 20px;
                        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
                        border-radius: 5px;
                    }
                    h1 {
                        color: #2196F3;
                        border-bottom: 1px solid #eee;
                        padding-bottom: 10px;
                    }
                    .meta {
                        background-color: #f9f9f9;
                        padding: 15px;
                        margin: 15px 0;
                        border-radius: 5px;
                        border-left: 4px solid #2196F3;
                    }
                    .meta p {
                        margin: 5px 0;
                    }
                    .content {
                        line-height: 1.6;
                    }
                    .categories {
                        margin: 15px 0;
                    }
                    .category {
                        display: inline-block;
                        background-color: #4CAF50;
                        color: white;
                        padding: 5px 10px;
                        margin-right: 5px;
                        border-radius: 3px;
                        font-size: 0.9em;
                    }
                    .authors {
                        margin-top: 20px;
                        padding-top: 15px;
                        border-top: 1px solid #eee;
                    }
                    .author {
                        margin-bottom: 10px;
                    }
                    .image {
                        max-width: 100%;
                        height: auto;
                        margin: 15px 0;
                        border-radius: 5px;
                        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                    }
                    .error {
                        color: red;
                        font-weight: bold;
                        text-align: center;
                        padding: 20px;
                    }
                    .back-link {
                        display: inline-block;
                        margin-top: 20px;
                        background-color: #2196F3;
                        color: white;
                        padding: 10px 15px;
                        border-radius: 4px;
                        text-decoration: none;
                    }
                    .back-link:hover {
                        background-color: #0b7dda;
                        text-decoration: none;
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
                    <xsl:choose>
                        <xsl:when test="rss:item">
                            <xsl:apply-templates select="rss:item"/>
                        </xsl:when>
                        <xsl:when test="error">
                            <div class="error">
                                <h2>Erreur</h2>
                                <p>ID: <xsl:value-of select="error/id"/></p>
                                <p>Status: <xsl:value-of select="error/status"/></p>
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <div class="error">
                                <h2>Article non trouvé</h2>
                            </div>
                        </xsl:otherwise>
                    </xsl:choose>
                    <a href="/rss25SB/resume/html" class="back-link">Retour à la liste des flux</a>
                </div>

                <footer>
                    <p>© 2025 Service REST RSS25. Tous droits réservés.</p>
                </footer>
            </body>
        </html>
    </xsl:template>

    <!-- Template for item -->
    <xsl:template match="rss:item">
        <h1><xsl:value-of select="rss:title"/></h1>

        <div class="meta">
            <p><strong>GUID:</strong> <xsl:value-of select="rss:guid"/></p>
            <p><strong>Date:</strong> 
                <xsl:choose>
                    <xsl:when test="rss:published">
                        <xsl:value-of select="rss:published"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rss:updated"/>
                    </xsl:otherwise>
                </xsl:choose>
            </p>
        </div>

        <div class="categories">
            <xsl:for-each select="rss:category">
                <span class="category"><xsl:value-of select="@term"/></span>
            </xsl:for-each>
        </div>

        <xsl:if test="rss:image">
            <img class="image" src="{rss:image/@href}" alt="{rss:image/@alt}"/>
        </xsl:if>

        <div class="content">
            <xsl:choose>
                <xsl:when test="rss:content/@type = 'html'">
                    <xsl:value-of select="rss:content" disable-output-escaping="yes"/>
                </xsl:when>
                <xsl:otherwise>
                    <p><xsl:value-of select="rss:content"/></p>
                </xsl:otherwise>
            </xsl:choose>
        </div>

        <div class="authors">
            <xsl:if test="rss:author">
                <h3>Auteurs</h3>
                <xsl:for-each select="rss:author">
                    <div class="author">
                        <p><strong>Nom:</strong> <xsl:value-of select="rss:name"/></p>
                        <xsl:if test="rss:email">
                            <p><strong>Email:</strong> <xsl:value-of select="rss:email"/></p>
                        </xsl:if>
                        <xsl:if test="rss:uri">
                            <p><strong>URI:</strong> <a href="{rss:uri}"><xsl:value-of select="rss:uri"/></a></p>
                        </xsl:if>
                    </div>
                </xsl:for-each>
            </xsl:if>

            <xsl:if test="rss:contributor">
                <h3>Contributeurs</h3>
                <xsl:for-each select="rss:contributor">
                    <div class="author">
                        <p><strong>Nom:</strong> <xsl:value-of select="rss:name"/></p>
                        <xsl:if test="rss:email">
                            <p><strong>Email:</strong> <xsl:value-of select="rss:email"/></p>
                        </xsl:if>
                        <xsl:if test="rss:uri">
                            <p><strong>URI:</strong> <a href="{rss:uri}"><xsl:value-of select="rss:uri"/></a></p>
                        </xsl:if>
                    </div>
                </xsl:for-each>
            </xsl:if>
        </div>
    </xsl:template>
</xsl:stylesheet>
