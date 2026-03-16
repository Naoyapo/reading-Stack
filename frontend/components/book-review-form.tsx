"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Field, FieldGroup, FieldLabel, FieldError } from "@/components/ui/field"
import { Spinner } from "@/components/ui/spinner"
import { BookOpen, CheckCircle2, AlertCircle } from "lucide-react"
import { ThemeToggle } from "@/components/theme-toggle"

interface FormData {
  reviewDate: string
  title: string
  author: string
  readingRange: string
  summary: string
  goodPoints: string
  learnings: string
  nextActions: string
}

interface SuccessResponse {
  message: string
  fileName: string
  filePath: string
}

interface ErrorResponse {
  errorMessage: string
}

export function BookReviewForm() {
  const [formData, setFormData] = useState<FormData>({
    reviewDate: new Date().toISOString().split("T")[0],
    title: "",
    author: "",
    readingRange: "",
    summary: "",
    goodPoints: "",
    learnings: "",
    nextActions: "",
  })

  const [isSubmitting, setIsSubmitting] = useState(false)
  const [successResult, setSuccessResult] = useState<SuccessResponse | null>(null)
  const [errorMessage, setErrorMessage] = useState<string | null>(null)
  const [validationErrors, setValidationErrors] = useState<{ reviewDate?: string; title?: string }>({})

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: value }))
    
    if (validationErrors[name as keyof typeof validationErrors]) {
      setValidationErrors((prev) => ({ ...prev, [name]: undefined }))
    }
  }

  const validate = (): boolean => {
    const errors: { reviewDate?: string; title?: string } = {}
    
    if (!formData.reviewDate) {
      errors.reviewDate = "記録日を入力してください"
    }
    if (!formData.title.trim()) {
      errors.title = "書籍タイトルを入力してください"
    }
    
    setValidationErrors(errors)
    return Object.keys(errors).length === 0
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    
    setSuccessResult(null)
    setErrorMessage(null)
    
    if (!validate()) {
      return
    }
    
    setIsSubmitting(true)
    
    try {
      const response = await fetch("http://localhost:8080/api/book-reviews", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      })
      
      if (response.status === 201) {
        const data: SuccessResponse = await response.json()
        setSuccessResult(data)
        setFormData({
          reviewDate: new Date().toISOString().split("T")[0],
          title: "",
          author: "",
          readingRange: "",
          summary: "",
          goodPoints: "",
          learnings: "",
          nextActions: "",
        })
      } else {
        const data: ErrorResponse = await response.json()
        setErrorMessage(data.errorMessage || "エラーが発生しました")
      }
    } catch {
      setErrorMessage("サーバーへの接続に失敗しました。サーバーが起動しているか確認してください。")
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <div className="flex items-center justify-between">
          <CardTitle className="flex items-center gap-2">
            <BookOpen className="size-5" />
            読書記録
          </CardTitle>
          <ThemeToggle />
        </div>
      </CardHeader>
      <CardContent>
        {successResult && (
          <Alert className="mb-6 border-emerald-500 bg-emerald-50 text-emerald-900 dark:bg-emerald-950 dark:text-emerald-100">
            <CheckCircle2 className="size-4 text-emerald-600 dark:text-emerald-400" />
            <AlertTitle>{successResult.message}</AlertTitle>
            <AlertDescription>
              ファイル名: {successResult.fileName}
            </AlertDescription>
          </Alert>
        )}

        {errorMessage && (
          <Alert variant="destructive" className="mb-6">
            <AlertCircle className="size-4" />
            <AlertTitle>エラー</AlertTitle>
            <AlertDescription>{errorMessage}</AlertDescription>
          </Alert>
        )}

        <form onSubmit={handleSubmit}>
          <FieldGroup>
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
              <Field data-invalid={!!validationErrors.reviewDate}>
                <FieldLabel htmlFor="reviewDate">
                  記録日 <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  type="date"
                  id="reviewDate"
                  name="reviewDate"
                  value={formData.reviewDate}
                  onChange={handleChange}
                  aria-invalid={!!validationErrors.reviewDate}
                />
                {validationErrors.reviewDate && (
                  <FieldError>{validationErrors.reviewDate}</FieldError>
                )}
              </Field>

              <Field data-invalid={!!validationErrors.title}>
                <FieldLabel htmlFor="title">
                  書籍タイトル <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  type="text"
                  id="title"
                  name="title"
                  value={formData.title}
                  onChange={handleChange}
                  placeholder="例: リーダブルコード"
                  aria-invalid={!!validationErrors.title}
                />
                {validationErrors.title && (
                  <FieldError>{validationErrors.title}</FieldError>
                )}
              </Field>
            </div>

            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
              <Field>
                <FieldLabel htmlFor="author">著者</FieldLabel>
                <Input
                  type="text"
                  id="author"
                  name="author"
                  value={formData.author}
                  onChange={handleChange}
                  placeholder="例: Dustin Boswell"
                />
              </Field>

              <Field>
                <FieldLabel htmlFor="readingRange">読んだ範囲</FieldLabel>
                <Input
                  type="text"
                  id="readingRange"
                  name="readingRange"
                  value={formData.readingRange}
                  onChange={handleChange}
                  placeholder="例: p.1-50"
                />
              </Field>
            </div>

            <Field>
              <FieldLabel htmlFor="summary">一言まとめ</FieldLabel>
              <Textarea
                id="summary"
                name="summary"
                value={formData.summary}
                onChange={handleChange}
                placeholder="この本・章を一言でまとめると..."
                rows={2}
              />
            </Field>

            <Field>
              <FieldLabel htmlFor="goodPoints">良かった点</FieldLabel>
              <Textarea
                id="goodPoints"
                name="goodPoints"
                value={formData.goodPoints}
                onChange={handleChange}
                placeholder="印象に残った部分や良かった点..."
                rows={3}
              />
            </Field>

            <Field>
              <FieldLabel htmlFor="learnings">使えそうな学び</FieldLabel>
              <Textarea
                id="learnings"
                name="learnings"
                value={formData.learnings}
                onChange={handleChange}
                placeholder="実際に活用できそうな学び..."
                rows={3}
              />
            </Field>

            <Field>
              <FieldLabel htmlFor="nextActions">次にやること</FieldLabel>
              <Textarea
                id="nextActions"
                name="nextActions"
                value={formData.nextActions}
                onChange={handleChange}
                placeholder="学びを活かして次にやること..."
                rows={2}
              />
            </Field>

            <Button type="submit" className="w-full" disabled={isSubmitting}>
              {isSubmitting ? (
                <>
                  <Spinner className="mr-2" />
                  送信中...
                </>
              ) : (
                "読書記録を保存"
              )}
            </Button>
          </FieldGroup>
        </form>
      </CardContent>
    </Card>
  )
}
